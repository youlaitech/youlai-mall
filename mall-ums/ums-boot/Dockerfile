# 基础镜像
FROM openjdk:17-jdk-alpine

# 维护者信息
MAINTAINER youlai <youlaitech@163.com>

RUN echo "https://mirrors.ustc.edu.cn/alpine/v3.7/main/" > /etc/apk/repositories && \
    apk --no-cache add tzdata && \
    cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone

# /tmp 目录作为容器数据卷目录，SpringBoot内嵌Tomcat容器默认使用/tmp作为工作目录，任何向 /tmp 中写入的信息不会记录进容器存储层
# 在宿主机的/var/lib/docker目录下创建一个临时文件并把它链接到容器中的/tmp目录
VOLUME /tmp

# 复制主机文件至镜像内，复制的目录需放置在 Dockerfile 文件同级目录下
ADD target/ums-boot.jar app.jar

# 容器启动执行命令
CMD java \
    -Xms128m \
    -Xmx128m \
    -Djava.security.egd=file:/dev/./urandom \
    -jar /app.jar

# 声明容器提供服务端口
EXPOSE 8801



