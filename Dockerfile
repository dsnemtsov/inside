FROM adoptopenjdk:11-jre-hotspot
ADD build/libs/testProject-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]