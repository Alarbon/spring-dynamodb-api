# Spring DynamoDB API

API REST de productos con Spring Boot 3 y Java 21 que persiste en DynamoDB. Para desarrollo usa DynamoDB Local en Docker, sin cuenta de AWS.

**Estado:** proyecto de práctica, funcional en local.

## Qué demuestra

- Arquitectura hexagonal: dominio (`Product`, `ProductService`), puertos de entrada y salida, y adaptadores REST y de persistencia separados.
- Uso del AWS SDK v2 con DynamoDB Enhanced Client.
- Validación de peticiones y manejo centralizado de errores (`GlobalExceptionHandler`).

## Endpoints

Base: `http://localhost:8081/api/v1/products`

| Método | Ruta | Acción |
| --- | --- | --- |
| `POST` | `/` | Crear un producto |
| `GET` | `/` | Listar productos |
| `GET` | `/{id}` | Obtener un producto |
| `DELETE` | `/{id}` | Eliminar un producto |

## Tecnologías

Java 21 · Spring Boot 3.4 (Web, Validation, Actuator) · AWS SDK v2 DynamoDB Enhanced · Lombok · Maven · Docker (`amazon/dynamodb-local`)

## Ejecución local

```bash
docker compose up -d      # DynamoDB Local en el puerto 8000
./mvnw spring-boot:run    # API en el puerto 8081
```

## Configuración

`src/main/resources/application.yml` apunta a DynamoDB Local (`http://localhost:8000`, región `eu-west-1`). Las credenciales que contiene son valores ficticios que DynamoDB Local acepta; para un entorno real deben venir de variables de entorno o del proveedor de credenciales de AWS, nunca del repositorio.

## Limitaciones

- Sin endpoint de actualización.
- Sin pruebas automatizadas propias más allá de la plantilla.
- La carpeta `target/` y la base local `docker/dynamodb/shared-local-instance.db` están versionadas y conviene excluirlas.

## Licencia

Pendiente de decidir.
