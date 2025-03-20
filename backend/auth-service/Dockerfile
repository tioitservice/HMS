# Use the official openjdk image from the Docker Hub
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file into the container
COPY target/auth-service*.jar auth-service.jar

# Expose the port on which the Auth service will run
EXPOSE 8082

# Command to run the jar file
ENTRYPOINT ["java", "-jar", "auth-service.jar"]
