FROM openjdk:17.0.2-buster

WORKDIR /app

ENV APPFILE idgcore-core-engine-0.0.1-SNAPSHOT.jar

EXPOSE 8099

COPY build/libs/${APPFILE} /app/
COPY .env /app/

SHELL ["/bin/bash", "-c"] 
RUN source .env
ENTRYPOINT ["/bin/bash", "-c", "java -jar ${APPFILE}"]
