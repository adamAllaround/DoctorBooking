# Appointment booking application
This application, in its final version, will be a rest api allowing to book doctor appointments. 
Its main goal is to exercise what we've already learned about Hibernate and put it into practice. 
The architecture and more in-depth description will be available on allaroundJava blog.

## User Stories
This is a set of user stories characterizing each application version. 
Each version will be placed in its own, dedicated branch when ready. 
No application roles are assumed to exist, therefore user stories are all written from a perspective of a "user".

### V0.1
Version 0.1 is a simple service infrastructure for adding meeting slots and booking appointments. 
Here are the stories for version 0.1.
* As user I can add a Doctor
* As a user I can add a Patient
* As user I can add a period in which doctor is available
**Acceptance Criterias:** 
    * Each meeting slot has a start and an end time
    * It's ok if doctor has overlapping meeting slots

* As patient I can Book given doctor at a given time
**Acceptance Criterias:** 
    * Only available slot can be booked
    * When slot is booked an appointment is registered
    * When slot is booked, the slot is marked as removed *
    
Version 0.1 code is available here. 
https://github.com/adamAllaround/DoctorBooking/releases

### V1.0
Version 1.0 is a rest service with exposed functionalities from version 0.1
* As a user I can access all the version 0.1 functionalities through Rest API. 

Version 1.0 code is available here. 
https://github.com/adamAllaround/DoctorBooking/releases