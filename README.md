# Transactional Outbox - Message Relay

## Build
- Java 21 (Temurin)
- Gradle 8+

docker compose up -d
docker build . -t to-mr:latest --no-cache


## Docker and Spring Boot (layers)

java -Djarmode=layertools -jar transactional-outbox-sba-0.0.1-SNAPSHOT.jar list

java -Djarmode=layertools -jar transactional-outbox-sba-0.0.1-SNAPSHOT.jar extract
