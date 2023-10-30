# DockerFile

# jdk17 Image Start
FROM openjdk:17-jdk
#인자 정리 - Jar
ARG JAR_FILE=build/libs/*.jar

# JAR 파일 복사
COPY ${JAR_FILE} app.jar

# JSP 파일 복사
COPY src/main/webapp/WEB-INF/jsp /src/main/webapp/WEB-INF/jsp

RUN ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime

ENTRYPOINT [ "java", "-Dspring.profiles.active=docker","-jar","app.jar" ]