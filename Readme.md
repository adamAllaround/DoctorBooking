# Appointment booking application
This application is a rest api allowing to book doctor appointments. 
Its main goal is to exercise what we've already learned about Hibernate and REST and put it into practice. 
The architecture and more in-depth description is available on [allaroundJava blog.](https://allaroundjava.com/complete-spring-hibernate-application/)

## User Stories
This is a set of user stories characterizing each application version. 
No application roles are assumed to exist, therefore user stories are all written from a perspective of a "user".

Version 1.0 is a simple service infrastructure for adding meeting slots and booking appointments. 
Here are the stories:
* As user I can add a Doctor
* As a user I can add a Patient
* As user I can add a period in which doctor is available for a visit
**Acceptance Criteria:** 
    * Each meeting slot has a start and an end time
    * It's ok if doctor has overlapping meeting slots

* As patient I can Book given doctor at a given time
**Acceptance Criterias:** 
    * Only available slot can be booked
    * When slot is booked an appointment is registered
    * When slot is booked, the slot is marked as removed *
    
Version 0.1 code is available in [version-0.1 branch](https://github.com/adamAllaround/DoctorBooking/tree/version-0.1). 

## The important aspects of the application

Here are some of the important features/frameworks which were exercised in the app: 

### Unit testing REST Controllers
You can find REST Controllers with MockMvc standaloneSetup [described in this article.](https://allaroundjava.com/unit-testing-spring-rest-controllers-mockmvc/)
The Unit tests can be [found here](https://github.com/adamAllaround/DoctorBooking/tree/master/DoctorBookingApplication/src/test/java/com/allaroundjava/controller) 
### REST Controller Integration Testing
REST Controller integration testing with *@SpringBootTest* is [described right here](https://allaroundjava.com/rest-controller-integration-testing/) on allAroundJavaBlog.
The integration tests themselves can be [found here](https://github.com/adamAllaround/DoctorBooking/tree/master/DoctorBookingApplication/src/test/java/com/allaroundjava/controller) 
### Designing a REST API with Api First approach and Swagger
The Full process of using Open Api YAML file with swagger codegen maven plugin is described in [this article](https://allaroundjava.com/api-first-rest-service-swagger/)
The YAML file can be found [here](https://github.com/adamAllaround/DoctorBooking/blob/master/DoctorBookingApplication/src/main/resources/swagger.yaml)
The .pom file setup can be found [here in the code](https://github.com/adamAllaround/DoctorBooking/blob/master/DoctorBookingApplication/pom.xml)


## Running the application
After you clone the source code from Github, go into the main application folder called DoctorBooking and run maven install
to generate an executable jar. 
```
mvn clean install
```
Run the jar file with 
```
$ java -jar DoctorBookingApplication/target/DoctorBooking-Application-1.0-SNAPSHOT-exec.jar
```
Visit the Swagger UI page to test the API
```
http://localhost:8080/api/swagger-ui.html
```