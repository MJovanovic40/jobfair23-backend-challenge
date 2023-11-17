# Nordeus Backend challenge

**IMPORTANT**: When the application starts, it creates the dummy users and logs their JWT tokens to the console.
The tokens are essential for using the application since every request requires authentication.

## The Task

The task is to create a RESTful backend service for the Auctions feature for the Top Eleven in Spring Boot, Java.
The service should provide a way for users (a.k.a. football managers) to participate in system created auctions and
acquire new players for their respective teams.

10 auctions are created every minute, and they last 1 minute each. Auctions are extended if a user makes a bid within
the last 5 seconds of the auction, and in such case, the auction is extended to 5 seconds.

Users can view all active auctions, and see the available information about the auction, current bids and bidders, and
the player on auction.

Users can bid on the auctions by spending their tokens (and join the auction by making their first bid).
After the auction closes, the highest bidder wins the auctioned player.
The initial bid price is 1 token, and each bid increases the bid price by 1 token.

Users should be notified if there are new bidders on the auction they have joined, if the auction closes, announcing the
winner, and when new auctions are generated.

The service should be:

- Secure - Protected against unauthorized / malicious uses,
- Scalable - The service should be built in a way so that it can be horizontally scaled (it should be able to support
  more instances of itself being run simultaneously),
- Easily deployable - Dockerfile should be created for ease of testing and deployment,

## Limitations and Assumptions

Since this only a single service in a complex system, I am going to assume the following:

- This service will likely need to communicate with other services responsible for the user data, player data, and
  notifying the users (push notifications, email, etc.), so this service will not implement such features.
- More than one node of this service is going to be run for scaling purposes, so a database is needed for storing the
  auction data. (In memory solution doesn't work.)
- Auctions are going to be created, extended and closed based on server time.
- Other services use authentication based on JWT.

## Approach

The REST API is going to be implemented in Spring Boot.

The data for this service will be stored using a MariaDB database, using Java Spring JPA to manipulate it.
Since this service doesn't implement features for Users and Players, and because their services are not available for
this challenge, simplified models will be created initially to simulate communication between services.

This service will be secured using JWT and an IP rate-limiting system.
Although the best practice in the real-world scenario for this kind of service would be to use tokens signed with an
asymmetric algorithm like RSA, and using a KMS (Key Management System) for distributing public keys, with token refresh,
the scope of this challenge isn't suitable for such a setup, and the service will use the HS256 algorithm signed with a
secret key instead.

For the time-based events, the service will implement the Scheduler.

## Requirements

- [x] Define the required entities and the database scheme for the feature.
    - [x] Design the database scheme.
    - [x] Implement the database model classes and repositories.
    - [x] Implement seeds for the simulated entities.
- [x] Implement the RESTful API for auctions.
    - [x] Implement all required services.
    - [x] Implement all required endpoints.
    - [x] Implement the scheduler for time-based actions.
- [x] Secure the service.
    - [x] Implement request rate limiting.
    - [x] Implement JWT authentication support.
- [x] Configure logging.
- [x] Write the Dockerfile for easier deployment.
- [x] Generate the documentation.

## Documentation

Code documentation is available via javadoc (`mvn javadoc:javadoc`), API documentation is available via SwaggerUI (
please reference the `application.properties` file to disable it in production),
and all other files (images, schemes, etc.) will be available in the `docs/` folder.

## Running the service

The service requires some environment variables in order to run:

- `PROFILE` (scheduler or noscheduler) - Scheduler profile runs the scheduled events for creating and ending auctions
  alongside everything else, which is the default option.
- `DATABASE_HOST` - The host of the database.
- `DATABASE_NAME` - The name of the database.
- `DATABASE_USERNAME` - The username of the user that will have permission to modify the database.
- `DATABASE_PASSWORD` - The password of the user.
- `JWT_SECRET` - Secret key used to sign JWT tokens.
- `PORT` - The port that the application runs on.

The service can be started in two ways:

### 1. Maven

Provide all the environment variables and run `mvn spring-boot:run` to run the application.

### 2. Docker

1. Rename/Copy the `.env.example` to `.env` and modify `<database_username>`, `<database_password>`, and `<jwt_secret>`
   accordingly.
   Please note that if you want to run the service with docker compose (recommended), you need to set
   the `DATABASE_HOST` to "mariadb" (or the name of the database service inside the `docker-compose.yml` file).
2. Run `docker compose up` and the service will start on the provided port.

Optionally you can build the Docker container and run your MariaDB instance independently.
In that case build the container from the provided Dockerfile and run it with the above-mentioned environmental
variables.
