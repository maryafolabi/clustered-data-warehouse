# Clustered Data

## Run Project
### Docker
```sh
docker-compose up -d
```
### Maven
- From application.properties in resources package, input your username and password for POSTGRES

```sh
 mvn clean spring-boot:run
```
## PROJECT DOCUMENTATION

# Project Packages
#### Configuration - project settings
- LocaleConfiguration - If during request a header key is set to 'language', then the value should be a valid locale
  for internalization of messages (Errors). Supported locales are "EN" (aka. English)

#### Controller
- POST - /deal - Create a deal containing valid payload, respond with validation error if not valid
- POST - /deals - Create list of deals containing valid payloads, respond with validation error if not valid
- GET - /deal/{unique-id} Get a deal by it's generated unique id
- GET - /deals {Paginated} Get all paginated deals (page default is at 20)
  validation error

#### Domain - Domain object Deal saved in database
### Properties
- UniqueId - Unique id of the particular deal. Datatype: UUID
- fromCurrency - ISO Currency from deal. Datatype: Currency
- toCurrency - ISO Currency to deal. Datatype: Currency
- timeStamp - Instantaneous time of making the deal. Datatype: Instant
- amount - Amount gotten for the deal> Datatype: BigDecimal

#### Logging
- Spring ASPECT is used to achieve Logging throughout this application
- Responses, Requests, Errors are being logged automatically for controllers, services and repositories packages
#### e2e
- A postman collection for all the endpoints in this application

#### Technology Used
- SPRINGBOOT
- POSTGRES
- DOCKER
- MAPSTRUCT
- LOMBOK
