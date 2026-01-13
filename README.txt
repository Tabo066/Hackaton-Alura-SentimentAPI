README – Proyecto Sentiment Analysis
Integración completa: Backend Java + DS Python + Frontend Streamlit + PostgreSQL
Entregable: Producto funcional con Docker Compose

¿Qué hace este proyecto?
Analiza el sentimiento de textos en español y devuelve:
Predicción (positivo/negativo/neutro)
Probabilidad de confianza
Gráfico de torta por lote
Descarga CSV con resultados

Cómo levantar el proyecto (1 comando)
Clonar el repositorio (o descomprimir el ZIP):
git clone <URL> SentimentFull
cd SentimentFull

Levantar todos los servicios (desde la raiz del proyecto en la terminal de vscode):
docker-compose up --build
Esperar hasta ver:
Todos los servicios iniciados

| Servicio       | URL                          | Descripción                              |
| -------------- | ---------------------------- | ---------------------------------------- |
| **Frontend**   | <http://localhost:8500>      | Interfaz web con gráficos y carga de CSV |
| **API Java**   | <http://localhost:8080>      | REST con integración DS real             |
| **DS Swagger** | <http://localhost:8000/docs> | Documentación interactiva del DS         |

 Cómo probar el funcionamiento
1. Texto individual (UI)
Abrir http://localhost:8500
Escribir un texto → Analizar
Ver predicción y probabilidad
2. CSV masivo (UI)
Arrastrar un .csv con columna texto
El archivo .csv debe estar en formato UTF-8 para ser procesado
Click en Procesar archivo
Ver tabla + gráfico de torta + descarga de resultados
3. API directa (cmd o curl)
curl -X POST http://localhost:8080/sentiment \
  -H "Content-Type: application/json" \
  -d '{"text":"me encanta este producto"}'
Respuesta: 
{ "prevision": "positivo", "probabilidad": 0.87 }

Revisar el contenido de la base de datos (PostgreSQL)
Dentro del cmd ejecutar como admin:
docker exec -it sentiment-postgres psql -U postgres -d sentimentdb

-- Ver tablas
\dt
-- Ver contenido de la tabla
SELECT * FROM prediction;
-- Contar registros
SELECT COUNT(*) FROM prediction;
-- Salir
\q

Herramientas utilizadas:

| Herramienta                 | Uso                    |
| --------------------------- | ---------------------- |
| **Java 17 + Spring Boot**   | Backend REST           |
| **FastAPI + Python 3.11**   | Microservicio DS       |
| **Streamlit + Python 3.11** | Frontend web           |
| **PostgreSQL 15**           | Persistencia           |
| **Docker + Docker Compose** | Orquestación unificada |
| **Maven**                   | Build de Java          |
| **Matplotlib**              | Gráficos en frontend   |
| **Flyway**                  | Migraciones (opcional) |

 Estructura del proyecto

SentimentFull/
├── docker-compose.yml          ← orquestador único
├── backend/
│   ├── Dockerfile
│   └── target/Sentiment-API-0.0.1-SNAPSHOT.jar
├── ds/
│   ├── Dockerfile
│   ├── app.py
│   ├── main.py
│   └── requirements.txt
├── frontend/
│   ├── Dockerfile
│   ├── app.py
│   └── requirements.txt
└── README.md                   ← este archivo