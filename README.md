# Crypto Dashboard

Aplicación de microservicios que consume la API de CoinGecko para mostrar criptomonedas en tiempo real. Los datos se actualizan automáticamente cada 5 minutos.

## Arquitectura utilizada: Layered Architecture (Arquitectura en capas)

| Servicio | Puerto | Responsabilidad |
|----------|--------|-----------------|
| crypto-consumer-service | 8081 | Consume CoinGecko y almacena los datos en cache |
| crypto-query-service | 8080 | API REST pública con Swagger |
| crypto-management-service | 8082 | Gestiona la watchlist personal |
| crypto-frontend | 4200 | Interfaz web en Angular |


## Tecnologías

- Java 21, Spring Boot 4.0, Maven
- Angular 21, Bootstrap 5
- Redis (cache), Docker
- springdoc-openapi, JUnit 5, Mockito

## Requisitos previos

- Docker Desktop instalado y corriendo

## Cómo correr el proyecto

- Ejecutar el siguiente comando:

docker compose up --build

Docker levanta todos los servicios automáticamente.

| URL | Descripción |
|-----|-------------|
| http://localhost:4200 | Frontend |
| http://localhost:8080/swagger-ui.html | Documentación API |


## Endpoints

### crypto-query-service — puerto 8080
| Método | URL | Descripción |
|--------|-----|-------------|
| GET | /items | Lista todas las criptomonedas |
| GET | /items/{id} | Detalle de una criptomoneda |

### crypto-management-service — puerto 8082
| Método | URL | Descripción |
|--------|-----|-------------|
| GET | /watchlist | Lista la watchlist |
| POST | /watchlist/{id} | Agrega a la watchlist |
| DELETE | /watchlist/{id} | Elimina de la watchlist |

## Tests

```bash
cd crypto-consumer-service && mvn test
cd crypto-query-service && mvn test
cd crypto-management-service && mvn test
```