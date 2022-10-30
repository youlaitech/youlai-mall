# 基础镜像
FROM openjdk:8-jre

# 维护者信息
MAINTAINER youlai <youlaitech@163.com>

# 设置容器时区为当前时区
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \&& echo 'Asia/Shanghai' >/etc/timezone

# /tmp 目录作为容器数据卷目录，SpringBoot内嵌Tomcat容器默认使用/tmp作为工作目录，任何向 /tmp 中写入的信息不会记录进容器存储层
# 在宿主机的/var/lib/docker目录下创建一个临时文件并把它链接到容器中的/tmp目录
VOLUME /tmp

# 复制主机文件至镜像内，复制的目录需放置在 Dockerfile 文件同级目录下
ADD target/admin-boot.jar app.jar

# 容器启动执行命令
ENTRYPOINT ["java", "-Xmx128m", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]

# 声明容器提供服务端口
EXPOSE 8800



