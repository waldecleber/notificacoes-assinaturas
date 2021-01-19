FROM openjdk:8 AS TEMP_BUILD_IMAGE
ENV APP_HOME=/app/
WORKDIR $APP_HOME
COPY build.gradle settings.gradle gradlew $APP_HOME
COPY gradle $APP_HOME/gradle
RUN ./gradlew build || return 0
COPY . .
RUN ./gradlew clean build

FROM openjdk:8-jre-slim
RUN mkdir /app
WORKDIR /app
COPY build/libs/*.jar /app/assinatura-globoplay-0.1.0-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-Dspring.profiles.active=prod", "-jar","assinatura-globoplay-0.1.0-SNAPSHOT.jar"]