#dockerfile takes from the target produced by the maven package
FROM openjdk:latest
#maven package produced "Group11Coursework-1.0-SNAPSHOT-jar-with-dependencies.jar"
COPY ./target/Group11Coursework-0.1.0.2-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "Group11Coursework-0.1.0.2-jar-with-dependencies.jar"]