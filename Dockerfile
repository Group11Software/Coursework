FROM openjdk:latest
COPY ./target/group11.jar /tmp
WORKDIR /tmp
#Dockerfile setup to work with dependencies of maven now
ENTRYPOINT ["java", "-jar","group11.jar", "world:3306", "1000"]
