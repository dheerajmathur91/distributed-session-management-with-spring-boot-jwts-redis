# Distributed Session Management with Spring Boot, JWTs and Redis

Description here

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development.

### Prerequisites

What things you need to install the software and how to install them
- JDK 1.7
- Redis
- Maven

### Installing

A step by step series of examples that tell you have to get a development env running

We will need to run this application on two different servers or two instances on single server to test our application is allowing session management across servers.

Here, we are running this application on two different ports on single machine.

Open terminal, reach to the project directory and execute following command:

```
mvn clean spring-boot:run -Dserver.port=8180
```

And in another terminal in the project directory, execute following command:

```
mvn clean spring-boot:run -Dserver.port=8280
```

## Running the tests

### Break down into end to end tests

Let's do the login first using following call:
```

```


## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Authors

* **Dheeraj Mathur** - *Initial work* - [PurpleBooth](https://github.com/dheerajmathur91)

