# Nordeus Backend challenge

## The Task
The task is to create a RESTful backend service for the Auctions feature for the Top Eleven in Spring Boot, Java.
The service should provide a way for users (a.k.a. football managers) to participate in system created auctions and acquire new players for their respective teams.

10 auctions are created every minute, and they last 1 minute each. Auctions are extended if a user makes a bid within the last 5 seconds of the auction, and in such case, the auction is extended to 5 seconds.

Users can first join the auction and see all available information about the auction, current bids and bidders, and the player on auction.

Users can bid on the auctions by spending their tokens, and after the auction closes, the highest bidder wins the auctioned player. The initial bid price is 1 token, and each bid increases the bid price by 1 token.

Users should be notified if there are new bidders on the auction they have joined, if the auction closes, announcing the winner, and when new auctions are generated.

The service should be:
 - Fast - The service should utilize data caching to provide a more 
 - Secure - Protected against unauthorized / malicious uses,
 - Scalable - The service should be built in a way so that it can be horizontally scaled (it should be able to support more instances of itself being run simultaneously),
 - Easily deployable - Dockerfile should be created for ease of testing and deployment,

## Limitations and Assumptions
Since this only a single service in a complex system, I am going to assume the following:
 - This service will likely need to communicate with other services responsible for the user data, player data, and notifying the users (push notifications, email, etc.), so this service will not implement such features.
 - More than one node of this service is going to be run for scaling purposes, so a database is needed for storing the auction data. (In memory solution doesn't work.)
 - Auctions are going to be created, extended and closed based on server time.
 - Other services use authentication based on JWT.

## Approach
The REST API is going to be implemented in Spring Boot. 

The data for this service will be stored using a MariaDB database, using Java Spring JPA to manipulate it.

This service will be secured using the Spring Security package, configured to authenticate requests using JWT.

For the time-based events, I am going to use the Spring Scheduler running on 1 second intervals.

## Requirements
  - [ ] Define the required entities and the database scheme for the feature.
    - [x] Design the database scheme.
    - [ ] Implement the database model classes and repositories.
  - [ ] Implement the RESTful API for auctions.
    - [ ] Implement all required services.
    - [ ] Implement all required endpoints.
    - [ ] Implement the scheduler for time-based actions. 
  - [ ] Secure the service.
  - [ ] Write the Dockerfile for easier deployment.
  - [ ] Generate the documentation.

## Documentation
Code documentation will be available via javadoc, API documentation will be available via SwaggerUI (only in development mode),
and all other files (images, schemes, etc.) will be available in the docs folder.
