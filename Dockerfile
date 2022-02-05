FROM maven:3.6.3-openjdk-11-slim as BIULDER
ARG VERSION=0.0.1-SNAPSHOT
WORKDIR /build/
COPY pom.xml /build/
COPY src /build/src/

RUN mvn clean package
COPY target/Task_3_1_1-${VERSION}.jar target/application.jar

FROM openjdk:11.0.13-jre-slim
WORKDIR /app/

COPY --from=BUILDER /build/target/application.jar /app/
COPY java -jar /app/application.jar

#FROM openjdk:11
#COPY . /usr/src/myapp
#WORKDIR /usr/src/myapp

ENV PORT 3000
EXPOSE $PORT

#RUN java Task311Application.java
#CMD ["java", "Task311Application"]