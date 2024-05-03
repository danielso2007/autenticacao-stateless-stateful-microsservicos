FROM amazoncorretto:17.0.11-alpine3.19
COPY @project.artifactId@-@project.version@.jar app.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "app.jar" ]
