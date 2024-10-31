FROM openjdk:latest
COPY ./target/classes/TESTPACKAGE.jar /tmp/TESTPACKAGE
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "TESTPACKAGE.jar", "db:3306", "10000"]
