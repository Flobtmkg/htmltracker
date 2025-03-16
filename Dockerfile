FROM eclipse-temurin:17

VOLUME /database

# Disable H2-Console
ENV SPRING_PROFILES_ACTIVE=prod

# Store data of the embeded RAM database inside a file on exit and auto reload it on init so it can be persisted
ENV SPRING_DATASOURCE_URL=jdbc:h2:file:/database/database;DB_CLOSE_ON_EXIT=FALSE;AUTO_RECONNECT=TRUE

ENV MAIL_USERNAME=
ENV MAIL_PASSWORD=
ENV MAIL_ENABLE=true
ENV MAIL_SMTP_HOST=smtp.google.com
ENV MAIL_SMTP_PORT=587

ENV SERVER_PORT=8080

COPY ./target/htmltracker-*.jar /htmltracker/htmltracker.jar

ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/htmltracker/htmltracker.jar" ]
