FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/youlai-gateway.jar youlai-gateway.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/youlai-gateway.jar"]
EXPOSE 9999
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \&& echo 'Asia/Shanghai' >/etc/timezone
