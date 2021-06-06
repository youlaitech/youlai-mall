FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/youlai-auth.jar youlai-auth.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/youlai-auth.jar"]
EXPOSE 8000
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \&& echo 'Asia/Shanghai' >/etc/timezone
