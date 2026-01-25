FROM eclipse-temurin:17-jdk-jammy
MAINTAINER rajesh.mylsamy
COPY target/tf-core-service-*.jar tf-core-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/tf-core-service-0.0.1-SNAPSHOT.jar"]