FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/pms-boot.jar mall-pms.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/mall-pms.jar"]
EXPOSE 8602
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \&& echo 'Asia/Shanghai' >/etc/timezone
