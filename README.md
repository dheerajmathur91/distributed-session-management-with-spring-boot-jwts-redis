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

We will need to run this application on two different servers or two instances on single server to test our application is allowing session management across servers.

Before this, lets first configure the appilcation properties located in distsession/src/main/resources directory:
```
# The host name of server where your redis is install.
# In my case redis is installed on my local system. Hence I've kept it as localhost.
redis.host = localhost

# The port at which redis is running. 6379 is the default port of redis.
redis.port = 6379

# The amount of idle time in minutes after which user will logout automatically if no activity is performed by user.
redis.idleTimeToInvalidateAuthToken = 30 

# Signing key which will be used by JWTs to create token.
redis.signingKey = "signingKey"
```

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

- Let's do the login first using following call:
```
POST localhost:8180/user/login

Header:
Content-Type:application/json

Body:
{
	"username": "dheeraj",
	"password": "mathur"
}
```
We will receive following response:
```
{
    "exception": false,
    "messages": [
        "Login Success!"
    ],
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaGVlcmFqIiwiaWF0IjoxNTIyMzg5MTU0fQ.IqncAT2E0fIN4URG-r3TXmNie8cED_BxxlQGJreL_V4"
}
```
This means we've successfully logged in. We've received a token in resposne which will be used to access the authenticated resources of the application in subsequent calls.

- To validate above, lets do another call which will require authentication

```
GET localhost:8180/user/logintest

Header:
Authorization:eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaGVlcmFqIiwiaWF0IjoxNTIyMzg5MTU0fQ.IqncAT2E0fIN4URG-r3TXmNie8cED_BxxlQGJreL_V4
```

And same call on different instance:
```
GET localhost:8280/user/logintest

Header:
Authorization:eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaGVlcmFqIiwiaWF0IjoxNTIyMzg5MTU0fQ.IqncAT2E0fIN4URG-r3TXmNie8cED_BxxlQGJreL_V4
```
Both of the above calls will provide us below result:
```
{
    "exception": false,
    "messages": [
        "Authentication Successful!"
    ]
}
```
Which means we've successfully authenticated request on two different instances of the application with the same auth token. 

- To logout from the application, make following call:
```
GET localhost:8180/user/logout

Header:
Authorization:eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaGVlcmFqIiwiaWF0IjoxNTIyMzg5MTU0fQ.IqncAT2E0fIN4URG-r3TXmNie8cED_BxxlQGJreL_V4
```
It will return following response:
```
{
    "exception": false,
    "messages": [
        "Logged out successfully!"
    ]
}
```
We've now successfully logged out of the application. Now if we try to access the authenticated resource again, it will return 401 unauthorized because user is logged out. 

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Authors

* **Dheeraj Mathur** - *Initial work* - [PurpleBooth](https://github.com/dheerajmathur91)

