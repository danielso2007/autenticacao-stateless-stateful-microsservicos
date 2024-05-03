FROM amazoncorretto:17.0.11-alpine3.19
COPY @project.artifactId@-@project.version@.jar app.jar
EXPOSE 8082
ENTRYPOINT [ "java", "-jar", "app.jar" ]
