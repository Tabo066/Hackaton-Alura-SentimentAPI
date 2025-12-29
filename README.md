# Hackaton-Alura-SentimentAPI

API REST (mock) para análisis de sentimiento de reseñas y comentarios.
Este backend será consumido por el frontend y, más adelante, integrará
el modelo de predicción del equipo de Data Science.

---

## Backend (Mock)

### Endpoint
**POST** `/analyze`

---
### Request
```json
{
  "text": "buen producto"
}
```
---
### Response
```json
{
"prediccion": "positivo",
"probabilidad": 0.87
}
```
---
### Puerto
```
http://localhost:8081
```
---

---
### Manejo global de excepciones

El proyecto cuenta con un manejo centralizado de excepciones mediante la clase GlobalExceptionHandler, implementada con la anotación @RestControllerAdvice.

Esta implementación permite:

Capturar errores de validación generados por @Valid.

Manejar de forma centralizada la excepción MethodArgumentNotValidException.

Proveer respuestas claras y estructuradas para el frontend.

Mejorar la comunicación y consistencia de los errores expuestos por la API.

---

### Control de versiones

Se incluye un archivo .gitignore para evitar versionar archivos generados durante la compilación, como los .class, así como el directorio target, ya que no deben subirse al repositorio.


---
## 🧪 Pruebas

El proyecto cuenta con pruebas unitarias para el controlador principal utilizando:

- Spring Boot Test
- MockMvc
- JUnit 5
- Mockito

### Casos cubiertos
- Request válido → 200 OK
- Request inválido (texto demasiado corto) → 400 Bad Request
