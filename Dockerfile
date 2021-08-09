#************************************************************
# Building
#************************************************************

# Add Image based on Gradle7 & JDK16
FROM maven:3.8.1-jdk-8 AS MAVEN_BUILD

# Set working directory
WORKDIR /building-workspace

# Copy source code to working directory
COPY ./ ./

# Build project
RUN mvn package -Dmaven.test.skip=true

#************************************************************
# Executing
#************************************************************

# Add Image based on OpenJDK16 & Alpine
FROM openjdk:8u212-jre-alpine3.9

# Copy only the artifacts we need from the first stage and discard the rest
COPY --from=MAVEN_BUILD /building-workspace/target/api-V1.3.0-SNAPSHOT.jar /tplate.jar

# Set the startup command to execute the jar
CMD ["java", "-jar", "/tplate.jar"]