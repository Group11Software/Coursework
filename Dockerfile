FROM openjdk:latest
COPY ./target/Group11Coursework-1.0-SNAPSHOT-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "Group11Coursework-1.0-SNAPSHOT-jar-with-dependencies.jar", "db:3306", "10000"]
