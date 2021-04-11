FROM openjdk:8-jdk-alpine

VOLUME /tmp

ADD target/*.jar PatientController.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","PatientController.jar"]