📊 Sistema de Encuestas – Java (Swing)

Aplicación de escritorio desarrollada en Java que permite crear, administrar y analizar encuestas, con manejo de roles, persistencia de datos y visualización gráfica de resultados.
El objetivo del proyecto es simular un sistema real de gestión y análisis de encuestas, aplicando buenas prácticas de diseño y separación de responsabilidades.

🚀 Funcionalidades principales
👤 Gestión de roles

Administrador

Crear, modificar y eliminar encuestas

Administrar preguntas y respuestas

Visualizar resultados y resúmenes gráficos

Promotor

Acceder a encuestas disponibles

Responder encuestas

Usuario

Registrar respuestas a encuestas

📝 Gestión de encuestas

Creación de encuestas con múltiples preguntas

Soporte para:

Respuestas predeterminadas

Respuestas de texto libre

Validación:

Si una encuesta tiene respuestas asociadas, solo se permite modificar el título

Registro de respuestas con fecha de realización

📈 Reportes y gráficos

Resumen de resultados por encuesta

Conteo de respuestas por pregunta

Visualización mediante:

Gráficos de barras

Gráficos circulares (según tipo de pregunta)

Filtros por:

Encuesta

Rango de fechas

🏗️ Arquitectura

El proyecto está organizado siguiendo una estructura en capas:

UI (Swing)
Interfaces gráficas separadas por rol

Service
Lógica de negocio y validaciones

DAO
Acceso a datos y persistencia

Interfaces
Desacoplamiento y mejor mantenibilidad

Este enfoque facilita la escalabilidad del sistema y una futura migración a una aplicación web.

🛠️ Tecnologías utilizadas

Java

Swing (interfaz gráfica)

DAO / Service Pattern

POO

Gráficos estadísticos para visualización de datos

🎯 Objetivo del proyecto

Aplicar conceptos de:

Programación orientada a objetos

Diseño en capas

Manejo de datos y reportes

Desarrollar una aplicación realista, más cercana a un entorno productivo que a un ejemplo académico.

📌 Posibles mejoras futuras

Migración a aplicación web (Spring Boot + Frontend)

Autenticación con usuarios y contraseñas

Exportación de resultados (PDF / Excel)

Dashboard más avanzado

📷 Capturas de pantalla

(opcional, pero muy recomendado para GitHub)

👨‍💻 Autor

Alan
Estudiante de Sistemas
Proyecto académico con enfoque profesional