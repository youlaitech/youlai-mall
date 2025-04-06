# 获取宿主机IP地址（获取IPv4地址，通常是有线或无线网卡的地址）
# 优先选择WLAN或以太网接口，排除WSL和其他虚拟适配器

function Get-PreferredIP {
    # 第一个方法：查找实际连接到网络的物理网卡
    $networkAdapters = Get-NetAdapter | Where-Object {
        $_.Status -eq 'Up' -and 
        -not ($_.Name -like "*WSL*" -or 
              $_.Name -like "*vEthernet*" -or
              $_.Name -like "*Hyper-V*" -or
              $_.Name -like "*Virtual*" -or
              $_.Name -like "*Loopback*")
    }
    
    foreach ($adapter in $networkAdapters) {
        $ip = Get-NetIPAddress -InterfaceIndex $adapter.ifIndex -AddressFamily IPv4 | 
              Where-Object { -not $_.IPAddress.StartsWith("169.254.") } |
              Select-Object -ExpandProperty IPAddress -First 1
        if ($ip) {
            Write-Host "Found IP $ip on network adapter: $($adapter.Name)" -ForegroundColor Cyan
            return $ip
        }
    }
    
    # 第二个方法：通过常见的网络适配器名称查找
    $HOST_IP = Get-NetIPAddress -AddressFamily IPv4 | Where-Object { 
        # 包含WLAN或以太网适配器，排除WSL和虚拟适配器
        ($_.InterfaceAlias -like "*WLAN*" -or 
         $_.InterfaceAlias -like "*Wi-Fi*" -or 
         $_.InterfaceAlias -like "*以太网*" -or 
         $_.InterfaceAlias -like "*Ethernet*") -and
        # 排除WSL和其他虚拟适配器
        -not ($_.InterfaceAlias -like "*WSL*" -or 
              $_.InterfaceAlias -like "*vEthernet*" -or 
              $_.InterfaceAlias -like "*Hyper-V*" -or
              $_.InterfaceAlias -like "*Virtual*")
    } | Select-Object -ExpandProperty IPAddress | Select-Object -First 1
    
    if ($HOST_IP) {
        return $HOST_IP
    }
    
    # 第三个方法：排除回环和保留IP，找到最可能的外部IP
    return Get-NetIPAddress -AddressFamily IPv4 | Where-Object { 
        -not $_.IPAddress.StartsWith("127.") -and
        -not $_.IPAddress.StartsWith("169.254.") -and 
        -not ($_.InterfaceAlias -like "*WSL*" -or 
              $_.InterfaceAlias -like "*vEthernet*" -or 
              $_.InterfaceAlias -like "*Hyper-V*" -or
              $_.InterfaceAlias -like "*Virtual*" -or
              $_.InterfaceAlias -like "*Loopback*")
    } | Select-Object -ExpandProperty IPAddress | Select-Object -First 1
}

# 主程序开始
try {
    # 尝试获取最佳IP地址
    $HOST_IP = Get-PreferredIP
    
    # 如果仍然无法获取IP，提示用户手动输入
    if (-not $HOST_IP) {
        Write-Host "Unable to automatically detect appropriate IP address, please enter your IP address:" -ForegroundColor Red
        $HOST_IP = Read-Host
    }
    
    # 检查IP格式是否有效
    if (-not ($HOST_IP -match "^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$")) {
        Write-Host "Warning: The IP address '$HOST_IP' does not appear to be a valid IPv4 address." -ForegroundColor Yellow
        Write-Host "Continue anyway? (y/n)" -ForegroundColor Yellow
        $continue = Read-Host
        if ($continue -ne 'y') {
            Write-Host "Operation cancelled by user" -ForegroundColor Red
            exit 1
        }
    }
    
    Write-Host "Detected host IP: $HOST_IP" -ForegroundColor Green
    Write-Host "Using this IP as SEATA_IP to start services" -ForegroundColor Green
    
    # 设置环境变量
    $env:SEATA_IP = $HOST_IP
    
    # 启动docker-compose
    Write-Host "Starting Docker services..." -ForegroundColor Cyan
    docker-compose -f ./docker-compose.yml -p youlai-mall up -d
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "Docker services started successfully" -ForegroundColor Green
    } else {
        Write-Host "Error starting Docker services" -ForegroundColor Red
    }
} catch {
    Write-Host "An error occurred: $_" -ForegroundColor Red
    exit 1
} 