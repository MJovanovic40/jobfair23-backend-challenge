# Nordeus Backend challenge

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
Since this service doesn't implement features for the Users and Players, they won't be included in the database,
but because their services are not available for this challenge, simple models will be implemented and loaded at runtime
to simulate communication between services.

This service will be secured using JWT and an IP rate-limiting system.
Although the best practice in the real-world scenario for this kind of service would be to use tokens signed with an
asymmetric algorithm like RSA, and using a KMS (Key Management System) for distributing public keys, with token refresh,
the scope of this challenge isn't suitable for such a setup, and the service will use the HS256 algorithm signed with a
secret key instead.

For the time-based events, I am going to use the Spring Scheduler.

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
- [ ] Generate the documentation.

## Documentation

Code documentation will be available via javadoc, API documentation will be available via SwaggerUI (only in development
mode),
and all other files (images, schemes, etc.) will be available in the docs folder.
