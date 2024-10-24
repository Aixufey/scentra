# Stage 1: Build the application using Maven
FROM maven:3.9-amazoncorretto-23 AS build
WORKDIR /home/app

# Copy only the pom.xml and download dependencies
COPY ./pom.xml /home/app/pom.xml
RUN mvn -f /home/app/pom.xml dependency:go-offline

# Copy the rest of the source code and build the app
COPY ./src /home/app/src
RUN mvn -f /home/app/pom.xml clean package -DskipTests

# Stage 2: Use a smaller base image to run the app
FROM openjdk:23-slim
WORKDIR /home/app

# Expose the port for the app
EXPOSE 8080

# Copy the built jar file from the previous stage
COPY --from=build /home/app/target/*.jar app.jar

# Run the app
ENTRYPOINT ["java", "-jar", "/home/app/app.jar"]



# Another example
## Reuse the layers cache for optimization
#FROM maven:3.9-amazoncorretto-23 AS build
#WORKDIR /home/app
## Copy the pom and Main class, less frequent changes
#COPY ./pom.xml /home/app/pom.xml
#COPY ./src/main/java/com/rinseo/scentra/ScentraApplication.java /home/app/src/main/java/com/rinseo/scentra/ScentraApplication.java
## Build the project
#RUN mvn -f /home/app/pom.xml dependency:go-offline
## Copy the rest, more frequent changes
#COPY . /home/app
#RUN mvn -f /home/app/pom.xml -DskipTests clean package
#FROM openjdk:23-slim
#EXPOSE 8080
#COPY --from=build /home/app/target/*.jar app.jar
#ENTRYPOINT ["java", "-jar", "app.jar"]
