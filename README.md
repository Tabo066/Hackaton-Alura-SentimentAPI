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

### 🚨 Logging de errores de validación (400)
La aplicación implementa un manejo centralizado de errores de validación para las solicitudes inválidas, registrando información relevante en los logs y devolviendo respuestas claras al cliente.
🔧 Manejo de errores
•	Se utiliza @RestControllerAdvice para capturar errores de forma global.
•	Los errores de validación se interceptan mediante MethodArgumentNotValidException.
•	Se construye una respuesta estructurada que indica:
o	El campo que falló la validación
o	El mensaje de error definido en el DTO

### 🧾 Logs de validación
•	Cuando ocurre un error de validación, se registra un log a nivel WARN.
•	El log contiene los campos inválidos y sus mensajes asociados.
•	No se exponen stacktraces ni información sensible en los logs.

Ejemplo de log:
WARN  GlobalExceptionHandler - Error de validación en request: {text=El texto debe tener al menos 10 caracteres}

### ✅ Buenas prácticas aplicadas
•	Separación de responsabilidades entre controladores y manejo de excepciones.
•	Uso del nivel de log adecuado (WARN para errores del cliente).
•	Respuestas HTTP estándar (400 Bad Request).
•	Código limpio, mantenible y fácil de extender.
---
### 🔴 Manejo de errores internos (500)

La aplicación implementa un manejo global de errores internos mediante @RestControllerAdvice, capturando excepciones no controladas (Exception.class).
Cuando ocurre un error 500, el sistema registra un log a nivel ERROR, incluyendo el stacktrace completo únicamente en los logs para facilitar el diagnóstico.

La respuesta al cliente es controlada y segura, devolviendo un mensaje genérico y amigable, sin exponer detalles internos del sistema.
Este enfoque garantiza una correcta trazabilidad de errores en el backend y una experiencia consistente para el consumidor de la API.

---

