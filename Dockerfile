FROM openjdk:17

# Application artifact name (should be updated according to application name in corresponding settings.gradle file)
ENV ARTIFACT 	UrlShortener
# Application artifact version (should be updated according to version in corresponding build.gradle file)
ENV VERSION 	0.0.1-SNAPSHOT
# Application artifact type (jar/war)
ENV EXTENSION 	jar

# Aplication root directory
RUN mkdir /usr/share/app
# Aplication logs root directory
RUN mkdir /usr/share/app/logs
# Mount logs directory to volume
VOLUME /usr/share/app/logs
# Set application root as a work directory
WORKDIR /usr/share/app/
# Copy artifact <app-name>-<app-version>.jar to /app/root/directory/app.jar
COPY /build/libs/${ARTIFACT}-${VERSION}.${EXTENSION} /usr/share/app/app.jar

# application external port
EXPOSE 8080


ENTRYPOINT ["java","-jar","/usr/share/app/app.jar"]