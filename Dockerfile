FROM gradle:7-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:17
ENV WORDCOUNT_TOPIC wordcount-topic
ENV BOOTSTRAP_SERVERS localhost:29092

RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/wordcount.jar
ENTRYPOINT ["java","-jar","/app/wordcount.jar"]