FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/admin-boot.jar admin-boot.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/admin-boot.jar"]
EXPOSE 8100
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \&& echo 'Asia/Shanghai' >/etc/timezone
