FROM openjdk:11
ADD build/libs/alpha-bank-test-0.0.1-SNAPSHOT.jar dockerapp.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "dockerapp.jar"]