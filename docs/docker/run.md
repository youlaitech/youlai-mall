
## 运行
```bash
powershell -Command "$env:HOST_IP = (Get-NetIPAddress -AddressFamily IPv4 | Where-Object { $_.InterfaceAlias -eq '以太网' -and $_.IPAddress -ne '127.0.0.1' }).IPAddress";
powershell -Command Write-Host "主机的 IP 地址是: $env:HOST_IP";

docker-compose -f .\docker-compose.yml -p youlai-mall up -d
```


## 卸载
```bash
docker-compose -f ./docker-compose.yml -p youlai-mall down
```

