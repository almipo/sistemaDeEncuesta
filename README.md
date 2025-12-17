# 📊 Sistema de Encuestas – Java Swing

> Aplicación de escritorio para crear, gestionar y analizar encuestas, desarrollada en Java con interfaz gráfica usando Swing.

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=java&logoColor=white)
![Desktop](https://img.shields.io/badge/Desktop-App-blue)
![Status](https://img.shields.io/badge/Status-Completed-success)

---

## 🧠 ¿Qué es este proyecto?

Este proyecto implementa un **sistema de encuestas** con:
- Gestión de encuestas, preguntas y respuestas
- Varios roles de usuario
- Persistencia de datos
- Reportes y gráficos estadísticos

El objetivo es simular una aplicación realista, aplicando buenas prácticas de arquitectura y diseño orientado a objetos. :contentReference[oaicite:1]{index=1}

---

## 🚀 Funcionalidades principales

### 👤 Roles
- **Administrador**
  - Crear, editar y eliminar encuestas
  - Administrar preguntas y respuestas
  - Visualizar resultados
- **Promotor**
  - Acceder y responder encuestas
- **Usuario**
  - Responder encuestas

### 📝 Encuestas y Respuestas
- Soporte para preguntas de opción múltiple y texto libre
- Validaciones:
  - Si una encuesta ya tiene respuestas asociadas, solo se permite editar el título
- Registro de respuestas con fecha de realización

### 📈 Reportes
- Conteo de respuestas por pregunta
- Visualización mediante gráficos de:
  - Barras
  - Circulares
- Filtros por encuesta y rango de fechas

---

## 🏗️ Arquitectura del proyecto
UI (Swing)
└── Service
└── DAO
└── Data Source


Separación clara entre:
- Interfaz gráfica (presentación)
- Lógica de negocio
- Persistencia de datos

Este enfoque facilita mantenimiento y futuras migraciones (por ejemplo, a una versión web). :contentReference[oaicite:2]{index=2}

---

## 🛠️ Stack tecnológico

- Java  
- Swing (GUI)  
- DAO / Service Pattern  
- Programación Orientada a Objetos  

---

## 📁 Estructura recomendada
sistemaDeEncuesta/
├── src/
├── bd/ # Base de datos o scripts SQL
├── libs/ # Bibliotecas externas necesarias
├── README.md
├── .gitignore
├── docs/ # Documentación adicional (opcional)
│ ├── diagrams/
│ └── screenshots/


---

## ▶️ Cómo ejecutar

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/almipo/sistemaDeEncuesta.git

Abrir el proyecto con IntelliJ IDEA

Ejecutar la clase principal (main) desde el IDE

🧪 Casos de uso cubiertos

Edición restringida de encuestas ya respondidas

Respuestas libres y predefinidas

Reportes por pregunta

Filtros por encuesta y rango de fechas

🔮 Roadmap (mejoras posibles)

 Migrar a aplicación web (Spring Boot + Frontend moderno)

 Autenticación por usuario/contraseña

 Exportación de resultados (PDF / Excel)

 Exportar dashboards interactivos

👨‍💻 Autor

Alan – Estudiante de Sistemas
Proyecto académico con foco profesional y orientado a buenas prácticas.
