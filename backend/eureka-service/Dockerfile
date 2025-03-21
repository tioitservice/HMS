# Use the official openjdk image from the Docker Hub
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file into the container
COPY target/eureka-service*.jar eureka-service.jar

# Expose the port on which the Eureka server will run
EXPOSE 8761

# Command to run the jar file
ENTRYPOINT ["java", "-jar", "eureka-service.jar"]
