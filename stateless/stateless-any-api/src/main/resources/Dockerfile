FROM amazoncorretto:17.0.11-alpine3.19
COPY target/@project.artifactId@-@project.version@.jar app.jar
EXPOSE 8081
ENTRYPOINT [ "java", "-jar", "app.jar" ]
