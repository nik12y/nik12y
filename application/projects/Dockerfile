FROM openjdk:17.0.2-buster

WORKDIR /app

ENV APPFILE build/libs/idgcore-core-engine-0.0.1-SNAPSHOT.jar

EXPOSE 8099

COPY ${APPFILE} /app/

ENTRYPOINT ["sh", "-c", "java -jar idgcore-core-engine-0.0.1-SNAPSHOT.jar"]
