FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/admin-boot.jar youlai-admin.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/youlai-admin.jar"]
EXPOSE 8100
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \&& echo 'Asia/Shanghai' >/etc/timezone
