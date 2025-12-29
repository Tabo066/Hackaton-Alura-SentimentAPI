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
- - Request inválido (texto demasiado corto) → 400 Bad Request

---

## 📝 Logging

El proyecto implementa logging básico utilizando Logback, con el objetivo de mejorar la trazabilidad y facilitar el diagnóstico durante el desarrollo y la ejecución de la API.

### 🔧 Configuración

Se utiliza Logback como framework de logging (configurado mediante logback-spring.xml).

Los logs se envían a la consola.

Nivel de logging configurado en INFO.

Formato de log incluye:

Fecha y hora

Nivel de log

Hilo de ejecución

Clase que genera el log

Mensaje descriptivo

### 📥 Logs de Request

Al recibir una solicitud al endpoint POST /analyze, se registra un log a nivel INFO indicando que la petición fue recibida.

No se registra el texto completo del request para evitar exponer información sensible.

Ejemplo:

INFO  SentimentController - Solicitud de análisis de sentimiento recibida

### 📤 Logs de Response

Antes de enviar la respuesta al cliente, se registra un log a nivel INFO con:

Predicción del sentimiento

Probabilidad asociada

Ejemplo:

INFO  SentimentController - Respuesta enviada: predicción=positivo, probabilidad=0.87

### ✅ Buenas prácticas aplicadas

Uso de niveles de log adecuados (INFO).

Uso de placeholders ({}) para mejorar rendimiento.

Evita loguear datos sensibles o innecesarios.

Logging implementado de forma clara y mantenible.

El sistema de logging permite monitorear el flujo básico de la aplicación y facilita el análisis durante pruebas y demostraciones.


---
