#!/bin/bash

# 设置颜色变量，用于输出美化
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
CYAN='\033[0;36m'
NC='\033[0m' # 无颜色

# 检查IP地址格式是否有效
is_valid_ip() {
    local ip=$1
    local stat=1
    
    if [[ $ip =~ ^[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}$ ]]; then
        IFS='.' read -r -a ip_parts <<< "$ip"
        [[ ${ip_parts[0]} -le 255 && ${ip_parts[1]} -le 255 && ${ip_parts[2]} -le 255 && ${ip_parts[3]} -le 255 ]]
        stat=$?
    fi
    
    return $stat
}

# 查找首选网络接口的IP地址
get_preferred_ip() {
    local ip=""
    local default_interface=""
    
    # 方法1: 尝试获取默认路由接口
    if command -v ip &>/dev/null; then
        default_interface=$(ip route | grep default | awk '{print $5}' | head -n 1)
        if [ -n "$default_interface" ]; then
            echo -e "${CYAN}Default network interface found: $default_interface${NC}"
            ip=$(ip -4 addr show dev $default_interface | grep -oP '(?<=inet\s)\d+(\.\d+){3}' | head -n 1)
            if [ -n "$ip" ] && [[ ! "$ip" == 127.* ]]; then
                echo -e "${CYAN}Found IP $ip on interface $default_interface${NC}"
                echo "$ip"
                return 0
            fi
        fi
    fi
    
    # 方法2: 使用 hostname -I 获取第一个IP地址
    ip=$(hostname -I | awk '{print $1}')
    if [ -n "$ip" ] && [[ ! "$ip" == 127.* ]]; then
        echo "$ip"
        return 0
    fi
    
    # 方法3: 尝试获取常见网络接口的IP
    local interfaces=("eth0" "en0" "wlan0" "wlp2s0" "ens33" "eno1" "enp0s3")
    for iface in "${interfaces[@]}"; do
        if command -v ip &>/dev/null; then
            ip=$(ip -4 addr show dev $iface 2>/dev/null | grep -oP '(?<=inet\s)\d+(\.\d+){3}' | head -n 1)
        elif command -v ifconfig &>/dev/null; then
            ip=$(ifconfig $iface 2>/dev/null | grep -oP '(?<=inet\s)\d+(\.\d+){3}' | head -n 1)
        fi
        
        if [ -n "$ip" ] && [[ ! "$ip" == 127.* ]]; then
            echo -e "${CYAN}Found IP $ip on interface $iface${NC}"
            echo "$ip"
            return 0
        fi
    done
    
    # 方法4: 最后尝试使用 ip 或 ifconfig 查找任何非回环地址
    if command -v ip &>/dev/null; then
        ip=$(ip -4 addr show scope global | grep -oP '(?<=inet\s)\d+(\.\d+){3}' | head -n 1)
    elif command -v ifconfig &>/dev/null; then
        ip=$(ifconfig | grep -oP '(?<=inet\s)\d+(\.\d+){3}' | grep -v '127.0.0.1' | head -n 1)
    fi
    
    if [ -n "$ip" ]; then
        echo "$ip"
        return 0
    fi
    
    # 如果所有方法都失败，返回空
    return 1
}

# 主程序开始
echo -e "${CYAN}Detecting host IP address...${NC}"

# 尝试获取最佳IP地址
HOST_IP=$(get_preferred_ip)

# 如果自动检测失败，提示用户手动输入
if [ -z "$HOST_IP" ] || [[ "$HOST_IP" == 127.* ]]; then
    echo -e "${RED}Unable to automatically detect appropriate IP address, please enter your IP address:${NC}"
    read HOST_IP
fi

# 验证IP格式
if ! is_valid_ip "$HOST_IP"; then
    echo -e "${YELLOW}Warning: The IP address '$HOST_IP' does not appear to be a valid IPv4 address.${NC}"
    echo -e "${YELLOW}Continue anyway? (y/n)${NC}"
    read continue
    if [[ ! "$continue" =~ ^[Yy]$ ]]; then
        echo -e "${RED}Operation cancelled by user${NC}"
        exit 1
    fi
fi

echo -e "${GREEN}Detected host IP: $HOST_IP${NC}"
echo -e "${GREEN}Using this IP as SEATA_IP to start services${NC}"

# 导出为环境变量，供docker-compose使用
export SEATA_IP=$HOST_IP

# 启动docker-compose
echo -e "${CYAN}Starting Docker services...${NC}"
docker-compose -f ./docker-compose.yml -p youlai-mall up -d

# 检查执行结果
if [ $? -eq 0 ]; then
    echo -e "${GREEN}Docker services started successfully${NC}"
else
    echo -e "${RED}Error starting Docker services${NC}"
    exit 1
fi 