# Person Service

This is a Person Service, which allows saving multiple Persons with unique ID's and names between 3 and 20 chars

## Requirements
· Implement a REST service called „PersonService“ which offers CRUD for an entity of type „Person“

· A person has a unique ID and a name which is non-null and has a length of 3 to 20 characters

· Use a technology stack of your choice, e.g. SpringBoot

· Use a database technology of your choice, e.g. MySQL, MongoDB or Neo4j

· Use a dependency and build management tool like Maven oder Gradle

· Implement an automated test, that tests the service remotely, we recommend using Arquillian

## Technology Stack

· JDK 1.8

· SpringBoot 2.2.0

· MongoDB 4.2

· Maven 3.5.3


## Installation


- Get MongoDB for your System: https://www.mongodb.com/download-center/community

-  Run your local Database:
```bash
cd %Installpath%\MongoDB\Server\%Version%\bin
mongod.exe
```

- Clone and import this project into your IDE
- Run Maven Update Project
- Start the Application by launching "PersonServiceApplication.java"


## Usage

Using Postman, use the given link and add the json to your Body using the application/json format
- Create a Person (POST):

```bash
localhost:8080/personService/create

{ "name": "YourName" }
```

- Get a Person with an ID (GET):

```bash
localhost:8080/personService/get/{id}
```

- Get all Persons (GET):

```bash
localhost:8080/personService/getAll

```

- Delete a Person (DELETE):

```bash
localhost:8080/personService/delete
{
  "id": "5db184e9167ec7606e216919",
  "name": "a323"
}

```

- Update a Person (PUT): 

```bash
localhost:8080/personService/update
{
  "id": "5db184e9167ec7606e216919",
  "name": "newName"
}

```

## License
[MIT](https://choosealicense.com/licenses/mit/)