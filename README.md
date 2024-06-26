# power-asset-optimiser
Service that optimises the power usage of assets.

# Run instructions
To run the app requires Docker and a minimum of Java 17.

1. Start up Postgres using docker-compose
```bash
docker-compose up -d
```
2. Build the application
```bash
./mvnw package
```

3. Run the application
```bash
./mvnw spring-boot:run
```

## Create an asset
```bash
curl --location 'localhost:8080/assets' \
--header 'Content-Type: application/json' \
--data '{
    "name": "abkd-rel-234-d",
    "dailyPowerUsage": 1350,
    "minimumPowerUsage": 37,
    "maximumPowerUsage": 92
}'
```

## Trigger the optimiser
Calculates and persists the power plan for all assets
```bash
curl --location --request POST 'localhost:8080/assets/power-plan' \
--data ''
```

## Activate an asset
Assuming that the assets can poll the server to get its power plan
```bash
curl --location 'localhost:8080/assets/abkd-rel-234-d'
```
