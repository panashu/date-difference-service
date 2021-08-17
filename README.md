# Calculate date difference between two dates

This service offers Rest API that takes in any date and returns the difference in number of days between the given date (the input) and the current date (Today’s
date).
Technical offerings : Spring Boot application.


# Swagger for visualizing API

Relevant specifications for consuming the REST API with the expected output is documented using OpenAPI standards.
This can be accessed at http://localhost:8080/swagger-ui.html


# Run

To build and run from a packaged jar locally:

    mvn spring-boot:run

or 

    mvn clean package -Dboot
    java -jar target/date-difference-service-1.0.0-SNAPSHOT.jar

# Docker

To build the image. First build the application, then build the docker image

    docker build -t ashu/date-difference-service-1.0.0-SNAPSHOT .
    
## Run

    docker run -d -p 8080:8080 ashu/date-difference-service-1.0.0-SNAPSHOT
    
# Test

    curl http://localhost:8080/api/datedifference?inputDate=<yyyy-mm-dd>
