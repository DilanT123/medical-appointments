# Sistema de Gestión de Citas Médicas

## Descripción
Este sistema de gestión de citas médicas es una aplicación backend desarrollada con Spring Boot que permite administrar eficientemente las citas médicas, historiales clínicos y la información de pacientes y médicos. El sistema implementa una arquitectura robusta siguiendo el patrón modelo/repositorio/controlador/servicio, con documentación completa mediante Swagger.

## Características Principales
- Gestión completa de citas médicas (programación, reprogramación y cancelación)
- Sistema de autenticación y autorización para diferentes roles de usuario
- Gestión de historiales médicos
- API RESTful documentada con Swagger
- Arquitectura por capas siguiendo las mejores prácticas de desarrollo
- Seguridad implementada con encriptación AES-256 para datos sensibles

## Tecnologías Utilizadas
- Java 17
- Spring Boot
- Spring Data JPA
- SpringDoc OpenAPI (Swagger)
- Maven
- Base de datos SQL
  
## Estructura del Proyecto
```
medical-appointments/
├── src/
│   ├── main/
│   │   ├── java/com/ups/medical/
│   │   │   ├── com.ups.medical.controllers/    # Controladores REST
│   │   │   ├── models/         # Entidades y modelos
│   │   │   ├── com.ups.medical.repositories/   # Interfaces de repositorio
│   │   │   ├── services/       # Lógica de negocio
│   │   │   └── security/       # Configuración de seguridad
│   │   └── resources/
│   │       └── application.properties
│   └── test/                   # Pruebas unitarias y de integración
├── docs/                       # Documentación adicional
└── README.md
```

## Requisitos de Instalación
1. JDK 17 o superior
2. Maven 3.6.3 o superior
3. Su IDE preferido (recomendado: IntelliJ IDEA o Eclipse)
4. Servidor SQL

## Configuración del Entorno
Base de Datos: [medical_db.sql](..%2FUsers%2Fmadel%2FDownloads%2Fmedical_db.sql)
 
## Documentación API

## Equipo de Desarrollo
- Torres Ollague Dilan Steven
- Conforme Paguay Madelein Kristel
- Izurieta Pineda Ariana Shantal
- Moreno Silva Kevin Fernando
  
## Licencia

## Contacto
