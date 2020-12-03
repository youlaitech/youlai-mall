FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
ADD target/${JAR_FILE} app.jar
EXPOSE 9999
ENTRYPOINT ["java","-jar","/app.jar"]

