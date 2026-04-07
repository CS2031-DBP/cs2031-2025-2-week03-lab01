# CS2031 - Week 03 Lab 01: Manejo de Errores en Spring Boot

## ¿Para qué sirve este repositorio?

Este repositorio extiende la Songs API del laboratorio anterior (`week02-lab02`) añadiendo soporte para `Album` y un manejo de errores robusto usando **Spring Boot** y **PostgreSQL**.

El objetivo principal de esta sesión de laboratorio es que los estudiantes comprendan cómo gestionar correctamente los errores en una API REST con Spring Boot:

1. **Excepciones de dominio vs. excepciones web**: diferenciar entre errores que pertenecen a la lógica de negocio (ej. canción no encontrada) y errores que pertenecen a la capa HTTP (ej. 404 Not Found).
2. **Flujo de errores en Spring**: cómo Spring resuelve qué manejador de excepción invocar según la presencia de `@ExceptionHandler`, `@ControllerAdvice` o `@ResponseStatus`.
3. **Estrategias de manejo de errores**: `ResponseStatusException`, `@ResponseStatus`, `@ExceptionHandler` y `@RestControllerAdvice`, y cuándo usar cada una.

---

## Cómo ejecutar el proyecto

### 1. Configurar las variables de entorno

Copia el archivo de ejemplo y completa los valores:

```bash
cp .env.example .env
```

El archivo `.env.example` ya incluye los valores por defecto para desarrollo local:

```env
DB_HOST=localhost
DB_PORT=5432
DB_NAME=week03-db
DB_USER=postgres
DB_PASSWORD=postgres
```

### 2. Levantar la base de datos con Docker

```bash
docker compose up -d
```

Esto levantará un contenedor PostgreSQL con la siguiente configuración:

| Parámetro    | Valor        |
|--------------|--------------|
| Host         | `localhost`  |
| Puerto       | `5432`       |
| Base de datos | `week03-db` |
| Usuario      | `postgres`   |
| Contraseña   | `postgres`   |

### 3. Ejecutar la aplicación

```bash
./mvnw spring-boot:run
```

La API estará disponible en `http://localhost:8080`.

> **Nota:** `spring.jpa.show-sql=true` está habilitado, por lo que verás el SQL generado por Hibernate en la consola — útil para entender qué consultas se ejecutan al interactuar con la API.

---

## Endpoints disponibles

### Songs

| Método   | Ruta          | Descripción                        | Posibles errores |
|----------|---------------|------------------------------------|------------------|
| `POST`   | `/songs/new`  | Crea una nueva canción             | `400` si el título es vacío o nulo |
| `GET`    | `/songs`      | Retorna todas las canciones        | — |
| `GET`    | `/songs/{id}` | Retorna una canción por ID         | `404` si no existe |
| `DELETE` | `/songs/{id}` | Elimina una canción por ID         | — |

### Albums

| Método   | Ruta                               | Descripción                             | Posibles errores |
|----------|------------------------------------|-----------------------------------------|------------------|
| `POST`   | `/albums`                          | Crea un nuevo álbum                     | — |
| `PATCH`  | `/albums/{id}/add-song/{song_id}`  | Añade una canción a un álbum            | `404` si el álbum o canción no existen · `409` si la canción ya está en el álbum |

### Ejemplo de body para crear una canción

```json
{
  "title": "Bohemian Rhapsody",
  "releaseDate": "1975-10-31",
  "duration": 354
}
```

### Ejemplo de body para crear un álbum

```json
{
  "title": "A Night at the Opera",
  "releaseDate": "1975-11-21",
  "label": "EMI"
}
```

---

## Arquitectura y estructura del proyecto

```
src/main/java/org/lab/week03lab01/
├── Week03Lab01Application.java           # Punto de entrada de Spring Boot
├── GlobalExceptionHandler.java           # Manejador global de excepciones (@RestControllerAdvice)
├── controller/
│   ├── SongController.java               # Endpoints de Song
│   └── AlbumController.java              # Endpoints de Album
├── service/
│   ├── SongService.java                  # Lógica de negocio de Song
│   └── AlbumService.java                 # Lógica de negocio de Album (contiene la actividad)
├── repository/
│   ├── SongRepository.java
│   ├── AlbumRepository.java
│   ├── ArtistRepository.java
│   └── GenreRepository.java
├── model/
│   ├── Song.java
│   ├── Album.java
│   ├── Artist.java
│   └── Genre.java
└── exceptions/
    ├── ResourceNotFoundException.java    # Excepción de dominio → 404
    └── ConflictException.java            # Excepción de dominio → 409
```

---

## Tipos de errores en Java

Spring Boot distingue dos grandes categorías dentro de `Throwable`:

- **`Error`**: problemas de los que normalmente no es posible recuperarse (ej. `OutOfMemoryError`). No deben ser capturados por el desarrollador.
- **`Exception`**: anomalías a nivel de aplicación que pueden ser gestionadas. Se dividen en:
  - **Checked exceptions**: el compilador obliga a manejarlas (ej. `IOException`).
  - **Unchecked exceptions** (`RuntimeException`): no requieren declaración explícita. Son las más usadas en Spring para errores de dominio.

En este proyecto, todas las excepciones personalizadas extienden `RuntimeException`:

```java
// exceptions/ResourceNotFoundException.java
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

// exceptions/ConflictException.java
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
```

---

## Flujo de errores en Spring

Cuando un `@Controller` lanza una excepción, Spring la resuelve en el siguiente orden:

```
Excepción lanzada desde el controller
         │
         ▼
¿Existe @ExceptionHandler en el mismo @Controller?
    Sí ──► Se maneja con ese método
    No ──► ¿Existe @ExceptionHandler en una clase @ControllerAdvice?
               Sí ──► Se maneja con ese método
               No ──► ¿Tiene @ResponseStatus la excepción?
                          Sí ──► ResponseStatusExceptionResolver la maneja
                          No ──► DefaultHandlerExceptionResolver la maneja
                                 (respuesta genérica de Spring)
```

El objetivo de este lab es llegar al segundo nivel: centralizar todos los manejadores en una clase `@RestControllerAdvice`.

---

## Estrategias de manejo de errores

### 1. `ResponseStatusException` — manejo inline en el controller

Lanza una excepción HTTP directamente desde el controller, mapeando una excepción de dominio a un código de estado HTTP:

```java
@GetMapping("/{id}")
public ResponseEntity<Song> getSongById(@PathVariable Long id) {
    try {
        return ResponseEntity.ok(songService.findById(id));
    } catch (ResourceNotFoundException e) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Song Not Found", e);
    }
}
```

> **Limitación:** dispersa el manejo de errores por todo el código. No es la estrategia recomendada para proyectos con múltiples controllers.

### 2. `@ResponseStatus` — anotación en la clase de excepción

Asocia un código HTTP directamente a una clase de excepción. Spring lo resuelve automáticamente sin código adicional en el controller:

```java
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Canción no encontrada")
public class ResourceNotFoundException extends RuntimeException { }
```

> **Limitación:** cuando se usa `reason`, Spring invoca `HttpServletResponse.sendError()`, lo cual hace que el contenedor Servlet escriba una página de error HTML — no apropiado para APIs REST. Para APIs, es preferible retornar un `ResponseEntity`.

### 3. `@ExceptionHandler` — método manejador en el controller

Permite definir un método dentro de un `@Controller` que intercepta excepciones específicas lanzadas por ese controller:

```java
@ExceptionHandler(ResourceNotFoundException.class)
public ResponseEntity<ErrorMessage> handleNotFound(ResourceNotFoundException ex) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(new ErrorMessage("Not Found", ex.getMessage()));
}
```

### 4. `@RestControllerAdvice` — manejador global ✅ recomendado

Centraliza todos los `@ExceptionHandler` en una sola clase que aplica a **todos los controllers** de la aplicación. Es la estrategia recomendada para APIs REST y la que se implementa en este proyecto:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail notFoundHandler(ResourceNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(404);
        problemDetail.setTitle("Resource Not Found");
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(ConflictException.class)
    public ProblemDetail conflictHandler(ConflictException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(409);
        problemDetail.setTitle("Conflict Error");
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }
}
```

`ProblemDetail` es un `RuntimeException` que implementa `ErrorResponse` y formatea el body de error según el estándar **RFC 9457** (*Problem Details for HTTP APIs*), retornando respuestas JSON como:

```json
{
  "type": "about:blank",
  "title": "Resource Not Found",
  "status": 404,
  "detail": "Song not found with id: 99",
  "instance": "/songs/99"
}
```

---

## Actividad

El método `AlbumService.addSong` ya tiene el esqueleto preparado. La actividad consiste en descomentar e implementar el manejo de las dos excepciones señaladas:

### Paso 1 — Excepción 404 en `AlbumService.addSong`

Reemplaza el `.orElseThrow()` vacío por uno que lance `ResourceNotFoundException` con un mensaje descriptivo:

```java
Song song = songRepository.findById(songId)
    .orElseThrow(() -> new ResourceNotFoundException("Song not found with id: " + songId));

Album album = albumRepository.findById(albumId)
    .orElseThrow(() -> new ResourceNotFoundException("Album not found with id: " + albumId));
```

### Paso 2 — Excepción 409 en `AlbumService.addSong`

Añade la validación que evita duplicados en el álbum:

```java
if (album.getSongs().contains(song)) {
    throw new ConflictException("Song with id: " + songId + " is already in album with id: " + albumId);
}
```

### Paso 3 — Handlers en `GlobalExceptionHandler`

Descomenta los dos métodos `@ExceptionHandler` en `GlobalExceptionHandler.java` para que Spring los registre y los use automáticamente cada vez que cualquier controller lance esas excepciones.

---

## Preguntas de reflexión

- ¿Por qué es recomendable retornar un `ResponseEntity` en los métodos error handler en lugar de solo lanzar `@ResponseStatus`?
- ¿Cuál es el beneficio de centralizar los manejadores en `@RestControllerAdvice` en lugar de definirlos dentro de cada `@Controller`?
- ¿Cómo se implementa una excepción personalizada en Spring Boot?

---

## Stack tecnológico

- **Java 21**
- **Spring Boot 3.5.5** (Web, Data JPA)
- **PostgreSQL 16** (via Docker)
- **Hibernate** (implementación JPA por defecto en Spring Boot)
- **Spring Data JPA** (repositorios)
- **RFC 9457 / ProblemDetail** (formato estándar de errores HTTP)
