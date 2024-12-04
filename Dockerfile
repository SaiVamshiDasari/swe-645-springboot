##/*Assignment-3
 #     Team Members
 #Sai Vamshi Dasari-G01464718
 #Aryan Sudhagoni-G01454180
 #Lahari ummadisetty-G01454186


# Use a lightweight base image
FROM openjdk:17-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the packaged JAR file into the container
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]

   