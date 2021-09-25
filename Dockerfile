FROM openjdk:11-jdk-slim AS TEMP_BUILD_IMAGE
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY build.gradle settings.gradle gradlew $APP_HOME
COPY gradle $APP_HOME/gradle
COPY . .
RUN ./gradlew build


FROM openjdk:11-jdk-slim
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/alpha-bank-test-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java","-jar", "alpha-bank-test-0.0.1-SNAPSHOT.jar"]