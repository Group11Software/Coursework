FROM openjdk:latest
COPY ./target/Group11Coursework-0.1.0.2-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "Group11Coursework-0.1.0.2-jar-with-dependencies.jar"]