# Use the official openjdk image from the Docker Hub
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file into the container
COPY target/user-service*.jar user-service.jar

# Expose the port on which the User service will run
EXPOSE 8081

# Command to run the jar file
ENTRYPOINT ["java", "-jar", "user-service.jar"]
