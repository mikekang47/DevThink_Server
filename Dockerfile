FROM openjdk:11
EXPOSE 8080
ADD build/libs/devthink-0.0.1.jar devthink-0.0.1.jar
ENTRYPOINT ["java", "-jar", "devthink-0.0.1.jar"]


