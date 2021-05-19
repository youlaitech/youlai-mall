FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/ums-boot.jar ums-boot.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/ums-boot.jar"]
EXPOSE 8100
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \&& echo 'Asia/Shanghai' >/etc/timezone