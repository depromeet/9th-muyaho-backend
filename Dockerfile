FROM adoptopenjdk/openjdk11:alpine-slim AS BUILD
ENV HOME=/usr/app
WORKDIR $HOME
COPY . $HOME
RUN ./gradlew clean build

FROM adoptopenjdk/openjdk11:alpine-jre
ENV HOME=/usr/app
COPY --from=BUILD  $HOME/muyaho-api/build/libs/muyaho-api-0.0.1-SNAPSHOT.jar /muyaho.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "muyaho.jar"]
