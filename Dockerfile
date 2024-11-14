FROM openjdk:latest
COPY ./target/Group11Coursework-1.0-SNAPSHOT-jar-with-dependencies.jar /tmp
WORKDIR /tmp
#Dockerfile setup to work with dependencies of maven now
ENTRYPOINT ["java", "-jar", "Group11Coursework-1.0-SNAPSHOT-jar-with-dependencies.jar", "db:3306", "1000"]
