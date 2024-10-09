FROM openjdk:latest
COPY ./target/classes/TESTPACKAGE /tmp/TESTPACKAGE
WORKDIR /tmp
ENTRYPOINT ["java", "org.example.App"]