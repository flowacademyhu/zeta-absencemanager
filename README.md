# Absence-Manager
### Description
This application was created for managing absences. There are employees, leaders and an admininstrator. Employees can make absence requests, which leaders have to approve first, then the administrator has to administrate. In the end, after the employee came back from his absence, either he/she or the administrator has to confirm it is done. At every status the absence can be declared rejected.
The administrator can also create groups and and users, and can see all the absences in the system. Leaders can view their employees and their absences.
### Requirements
* JRE 11
* Nodejs (8.15.0+)
* Angular 8 (with cli)
### Usage (Linux/MacOS)
##### Run server
./mvnw spring-boot:run
##### Run frontend
cd frontend
ng serve### Authors