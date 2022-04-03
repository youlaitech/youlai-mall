FROM openjdk:8-jre
MAINTAINER youlai youlaitech@163.com

RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \&& echo 'Asia/Shanghai' >/etc/timezone

VOLUME /tmp

ADD target/ums-boot.jar mall-ums.jar

ENTRYPOINT ["java", "-Xmx128m", "-Djava.security.egd=file:/dev/./urandom","-jar","/mall-ums.jar"]

EXPOSE 8601
