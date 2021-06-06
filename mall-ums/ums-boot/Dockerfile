FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/ums-boot.jar mall-ums.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/mall-ums.jar"]
EXPOSE 8601
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \&& echo 'Asia/Shanghai' >/etc/timezone
