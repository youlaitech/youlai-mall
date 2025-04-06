# Docker 部署说明

## 前置条件

1. 安装 Docker 和 Docker Compose
2. 确保已获取项目代码并切换到正确的目录

## 快速启动

### Windows 系统

```powershell
# 切换到 docker 目录
cd ./docs/docker

# 运行启动脚本 (会自动检测IP地址)
./start-windows.ps1
```

如果遇到脚本执行权限问题，先运行：
```powershell
Set-ExecutionPolicy RemoteSigned -Scope CurrentUser
```

### Linux/Mac 系统

```bash
# 切换到 docker 目录
cd ./docs/docker

# 添加执行权限
chmod +x ./start.sh

# 运行启动脚本 (会自动检测IP地址)
./start.sh
```

## 停止服务

### Windows 系统

```powershell
# 切换到 docker 目录
cd ./docs/docker

# 运行停止脚本
./stop-windows.ps1
```

### Linux/Mac 系统

```bash
# 切换到 docker 目录
cd ./docs/docker

# 添加执行权限
chmod +x ./stop.sh

# 运行停止脚本
./stop.sh
```

## 手动管理

如果不使用脚本，也可以直接使用docker-compose命令：

### 启动服务

```bash
docker-compose -f ./docker-compose.yml -p youlai-mall up -d
```

### 停止服务

```bash
docker-compose -f ./docker-compose.yml -p youlai-mall down
```

## 故障排除

### 无法启动 Seata 服务

如果 Seata 服务无法正常启动或注册到 Nacos，可能是由于自动检测的 IP 地址不正确。这种情况下，可以编辑 docker-compose.yml 文件，手动设置正确的 IP 地址：

```yaml
seata:
  environment:
    - SEATA_IP=192.168.x.x  # 替换为你的实际IP地址
```

### Windows 下乱码问题

如果在 Windows PowerShell 中运行脚本时出现乱码，可以尝试以下方法：

1. 使用 UTF-8 编码打开 PowerShell：
   ```powershell
   powershell -encodedCommand ([Convert]::ToBase64String([Text.Encoding]::Unicode.GetBytes('./start-windows.ps1')))
   ```

2. 修改 PowerShell 的字符编码：
   ```powershell
   chcp 65001
   ```

3. 使用 Windows Terminal 应用而不是普通 PowerShell 窗口

### 虚拟网络适配器问题

如果脚本错误地选择了虚拟网络适配器的 IP（如WSL的虚拟IP 172.x.x.x），而不是你的实际网络 IP，脚本会提供手动输入选项。输入你的实际 IP 地址即可。


