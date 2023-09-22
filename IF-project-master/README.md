### Role Based Access Control
#### Logic For Security [02244] - Spring 22

This project is a client / server implementation with Java RMI. 

In order to run the print tasks from the client, the server needs to be running.

When starting the server, you can choose whether to use Access control list or Role Based Access Control.

To show the changes that have to be made we made new json files, access-control-after-change and employees-roles-after-change,
and gave Henry and Ida passwords (now commented out). When Bob leaves the company he should lose his access to the system, 
roles and permissions. When Henry and Ida join the company they should get access to the system, get roles and permissions. 
When George gets a new role, this role should be added to his list of roles and get these additional permissions.


---

### Info 4 us

* Usernames and their corresponding passwords are found in the [src\main\java\rmi\server\AuthManager.java](src/main/java/rmi/server/AuthManager.java) file
* Authored roles can be found in the JSON files within the [src\access](src/access) folder
    * access-control
    * employees-roles
    * roles
* Run the program by;
    1. Running the server via [src\main\java\rmi\server\PrintServer.java](src/main/java/rmi/server/PrintServer.java)
    2. Running the client via [src\main\java\rmi\client\Client.java](src/main/java/rmi/client/Client.java)


---

# To Do List

## The Report
- [ ] Contribution Section
    - [ ] State who was responsible for each section
- [ ] Resources used to perform the work.
  - Text books, research papers (which approach we took), web material, suggestion from TAs, and discussions made with other groups
    - [ ] The solution must be based on one of the three approaches (or combined methods)
      * Denning-Denning
      * Volpano
      * Myers


## The Code

#### In Development
- [ ] Core environment
  - [ ] Client
  * For the input data
  - [ ] Server
  * For the output data 
  - [ ] Database
  * For results, history, and statistical information storage
- [ ] Information Flow Analysis
  * Based on the access labels that are involved in every step

#### In Practice
- [ ] Patients can book online appointments for tests and vaccinations
- [ ] Patients test results are stored in a database
- [ ] Patients vaccination history is stored in a database
- [ ] Patients can retrieve test results and their vaccination certificate from the database
- [ ] The Public can retrieve statistical information from the database (e.g. number of infections within the past seven days)

