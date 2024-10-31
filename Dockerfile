FROM openjdk:latest
COPY ./target/classes/TESTPACKAGE /tmp/TESTPACKAGE
WORKDIR /tmp
ENTRYPOINT ["java", "TESTPACKAGE.App", "db:3306", "10000"]
