#
# Build stage
#
FROM maven:4.0.0-openjdk-21 AS build
COPY src /sarinventory-be-final/src
COPY pom.xml /sarinventory-be-final
RUN mvn -f /sarinventory-be-final/pom.xml clean package
EXPOSE 8080
ENTRYPOINT ["java","-jar","/sarinventory-be-final/target/spring_rest_docker.jar"]