#!/bin/bash
remote_tag_ext=$1
port=$2
docker_hub_namespace=$3
module_name_suffix=$4
DOCKER_USERNAME=$5
DOCKER_PASSWORD=$6
# 登陆harbor
echo "$DOCKER_PASSWORD" | docker login http://harbor.howlaisi.com:30002 -u "$DOCKER_USERNAME" --password-stdin
# 停掉容器
if [ -n "$docker_hub_namespace" -a -n "$module_name_suffix" ]
then
  docker stop $(docker ps -a | grep "${docker_hub_namespace}_${module_name_suffix}" | awk '{print $1}') || true
  docker rm -f $(docker ps -a | grep "${docker_hub_namespace}_${module_name_suffix}" | awk '{print $1}') || true
  docker rmi -f $(docker images -a | grep "${module_name_suffix}" | awk '{print $3}') || true
fi
# 拉取最新镜像
docker pull "${remote_tag_ext}"
# 运行容器
docker run --restart=always -di --net=host -e "SPRING_PROFILES_ACTIVE=dev" --name="${docker_hub_namespace}_${module_name_suffix}" "${remote_tag_ext}"