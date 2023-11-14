FROM amazoncorretto:17
WORKDIR /app
COPY /target/*.jar /app/api.jar
EXPOSE 8080
CMD [ "java", "-jar", "api.jar" ]

# JPA E DATASOURCE
# ${DB_HOST:localhost}
# ${DB_SCHEMA}
# ${DB_USER}
# ${DB_PASS}

# CORS
# ${ORIGEM_PERMITIDA}

# MAIL
# ${MAIL_HOST}
# ${MAIL_USER}
# ${MAIL_PASS}

# S3
# ${S3_ACCESS_KEY}
# ${S3_SECRET_KEY}
