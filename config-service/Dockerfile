FROM adoptopenjdk/openjdk11:alpine-jre
RUN apk --no-cache add curl
RUN mkdir /opt/app
COPY target/config-service-1.0-SNAPSHOT.jar /opt/app
HEALTHCHECK --interval=10s --timeout=10s --start-period=20s --retries=30 CMD curl -f http://localhost:${CONFIG_SERVICE_PORT:-8888}/actuator/health || exit 1
CMD ["java", "-jar", "/opt/app/config-service-1.0-SNAPSHOT.jar"]