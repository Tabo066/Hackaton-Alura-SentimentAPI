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
```json
http://localhost:8081
```
---
