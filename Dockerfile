# Start with a base image containing Java runtime
FROM openjdk:17-alpine as build

# Add Maintainer Info
LABEL maintainer="phrcorreia3392@gmail.com"

# Set the current working directory inside the image
WORKDIR /app

# Copy maven executable to the image
COPY mvnw .
COPY .mvn .mvn

# Copy the pom.xml file
COPY pom.xml .

# Fetch the dependencies
RUN ./mvnw dependency:go-offline -B

# Copy the project source
COPY src src

# Package the application
RUN ./mvnw package -DskipTests

# Run the application
FROM openjdk:17-alpine
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

