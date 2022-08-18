FROM openjdk:17-jdk-alpine as builder
WORKDIR /app

# Copy maven executable to the image
COPY mvnw .
COPY .mvn .mvn

# Copy the pom.xml file
COPY pom.xml .

# Fix permissions for mvnw executable
RUN chmod +x mvnw

# Download all dependecies
RUN ./mvnw dependency:go-offline -B


# Copy the project source
COPY ./src ./src
COPY ./pom.xml ./pom.xml

RUN ./mvnw package -DskipTests -Dmaven.gitcommitid.skip=true

FROM tomcat:jre17

RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=builder /app/target/catalog-rs.war /usr/local/tomcat/webapps/catalog-rs.war
EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /usr/local/tomcat/webapps/catalog-rs.war" ]