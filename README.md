# 🥘 GrandmasFoodProyect

## 📌 Descripción
Este es el proyecto del training especifico para el negocio de GrandmasFood. Implementa *arquitectura hexagonal*, facilitando la escalabilidad y mantenibilidad.

🔹 *Tecnologías utilizadas*:
- 🖥️ *Spring Boot 3.4.2*
- 📦 *Spring Data JPA* (Persistencia)
- 🐘 *mySQL*
- 🎭 *Mockito + JUnit 5* (Pruebas Unitarias)
- 🔍 *Swagger (OpenAPI 3)* (Documentación de API)

## 📁 Arquitectura Hexagonal
El proyecto sigue el patrón de *arquitectura hexagonal*, con las siguientes capas:
📦 GrandmasFoodProduct ┣ 📂 application # Capa de aplicación (Casos de uso) ┣ 📂 domain # Capa de dominio (Modelos y lógica de negocio) ┃ ┣ 📂 models # Entidades del dominio ┃ ┣ 📂 exceptions # Excepciones de negocio ┃ ┗ 📂 spi # Interfaces de persistencia ┣ 📂 infrastructure # Capa de infraestructura (Adaptadores) ┃ ┣ 📂 rest # Controladores REST ┃ ┣ 📂 jpa # Adaptador de persistencia con JPA ┃ ┣ 📂 configuration # Configuraciones de Spring Boot ┃ ┗ 📂 exception # Manejadores globales de excepciones
