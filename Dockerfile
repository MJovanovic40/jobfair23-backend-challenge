FROM maven:3.8.5-openjdk-17 as builder

WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:17-oracle

WORKDIR /app
COPY --from=builder /app/target/AuctionService-0.0.1-SNAPSHOT.jar /app/auction-service.jar

CMD ["java", "-jar", "auction-service.jar"]