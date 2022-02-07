###########
# stage 1 #
###########
# Using the FROM command, the Dockerfile creates an image called 'build' from the openJDK image
FROM openjdk:11-slim as build
LABEL maintainer="Sean Maxwell <sa.maxwell@yahoo.com>"

ARG JAR_FILE

# Add the application's jar to the container
COPY ${JAR_FILE} app.jar

#unpackage jar file
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf /app.jar)

###########
# stage 2 #
###########
FROM openjdk:11-slim
VOLUME /tmp

#Copy unpackaged application to new container
ARG DEPENDENCY=/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","com.optimagrowth.license.LicenseServiceApplication"]