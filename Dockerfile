FROM openjdk:latest
COPY ./target/classes/TESTPACKAGE.App /tmp/TESTPACKAGE
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "TESTPACKAGE.App", "db:3306", "10000"]
