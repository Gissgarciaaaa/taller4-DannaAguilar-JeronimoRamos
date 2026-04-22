# bookstore-api

Proyecto Spring Boot para el taller de API REST de librería en línea.

## Requisitos
- Java 17+
- Maven 3.8+

## Ejecución
```bash
mvn spring-boot:run
```

## Rutas importantes
- Swagger: `http://localhost:8080/api/v1/swagger-ui/index.html`
- H2 Console: `http://localhost:8080/api/v1/h2-console`

## Usuario admin precargado
- email: `admin@bookstore.com`
- password: `Admin1234`

## Notas
- El contexto base es `/api/v1`
- Las respuestas exitosas usan `ApiResponse`
- Los errores usan `ApiErrorResponse`
