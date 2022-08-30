FROM openjdk:17.0.2-buster

WORKDIR /app

ENV APPFILE idgcore-core-engine-0.0.1-SNAPSHOT.jar

EXPOSE 8099

COPY build/libs/${APPFILE} /app/

ENTRYPOINT ["sh", "-c", "java -jar ${APPFILE} --DATABASE_URL=$DB_HOST --DB_USER_NAME=$DB_USER --DB_USER_PASSWORD=$DB_PASS --DB_DIALECT=org.hibernate.dialect.PostgreSQL95Dialect --DB_DRIVER_CLASSNAME=org.postgresql.Driver"]