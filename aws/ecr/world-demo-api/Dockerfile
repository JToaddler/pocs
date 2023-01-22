FROM amazoncorretto:11
ENTRYPOINT ["/usr/bin/java", "-jar", "/tmp/app/app.jar"]

ADD target/lib           /tmp/app/lib
ARG JAR_FILE
ADD target/${JAR_FILE} /tmp/app/app.jar