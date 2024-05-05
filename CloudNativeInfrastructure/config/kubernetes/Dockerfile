# Use a base image with JDK 11 installed (you can choose a version that matches your project's requirements)
FROM openjdk:11

# Set the working directory inside the container
WORKDIR /app

# Copy the local code to the container's workspace
COPY src /src/main

# Add the necessary build tools and compile your Java project
# Assuming you're using plain javac for compilation, adjust as necessary for your build tool (e.g., Maven, Gradle)
RUN javac -d /app/bin /BackendSystemDatabase/WeatherForecastBackendMicroservice/src/main/java/*.java

# Set the entry point to run your compiled Java program
# Replace `YourMainClass` with the class file that contains your main method
ENTRYPOINT ["java", "-cp", "/app/bin", "WeatherForecastConsumer"]

# Expose the port your application uses, replace `8080` with your port number if different
EXPOSE 8080
