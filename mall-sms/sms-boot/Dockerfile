FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/sms-boot.jar mall-sms.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/mall-sms.jar"]
EXPOSE 8604
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \&& echo 'Asia/Shanghai' >/etc/timezone
