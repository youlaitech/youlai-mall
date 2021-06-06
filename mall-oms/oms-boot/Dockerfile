FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/oms-boot.jar mall-oms.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/mall-oms.jar"]
EXPOSE 8603
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \&& echo 'Asia/Shanghai' >/etc/timezone
