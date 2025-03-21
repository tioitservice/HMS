# Use the official openjdk image from the Docker Hub
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file into the container
COPY target/api-gateway*.jar api-gateway.jar

# Expose the port on which the API Gateway will run
EXPOSE 8080

# Command to run the jar file
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]
