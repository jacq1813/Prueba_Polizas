# Sistema de Gestión de Pólizas

El sistema permite gestionar el ciclo de vida de pólizas de seguro (CRUD).

## Tecnologías Utilizadas

### Backend

- **Lenguaje:** Java 21
- **Framework:** Spring Boot 2.7.1
- **Seguridad:** Configuración de variables de entorno con `spring-dotenv`.

### Frontend

- **Framework:** Angular 19
- **Estilos:** Bootstrap

### Base de Datos

- **Motor:** PostgreSQL

---

## requisitos

tener instalado lo siguiente en tu entorno local:

- Java JDK 17+
- Node.js y NPM (para Angular)
- PostgreSQL
- ## Git

## Instalación y cnfiguración

Pasos orden para levantar el proyecto localmente.

### 1. Base de Datos

1.  Abre pgAdmin
2.  Crea una base de datos llamada `PolizasDB`.
3.  Abre el archivo `database.sql` que se encuentra en la raíz de este repositorio.
4.  Ejecuta todo el script para crear las tablas, los Stored Procedures y cargar los datos de prueba (Seed Data).

### 2. Backend (spring boot)

1.  Navega a la carpeta del backend "polizas":

    ```bash
    cd polizas
    ```

2.  **IMPORTANTE:** Crea un archivo llamado `.env` dentro de la carpeta `polizas` (al mismo nivel que `pom.xml`) y define las credenciales de base de datos:

    ```properties
    DB_URL=jdbc:postgresql://localhost:5432/PolizasDB
    DB_USER=postgres
    DB_PASSWORD=tu_contraseña_aqui
    ```

3.  Instala las dependencias y ejecuta el servidor:

    ```bash
    ./mvnw spring-boot:run
    ```

    _El servidor iniciará en el puerto `8080`._

### 3. Frontend (Angular)

1.  Abre una nueva terminal y navega a la carpeta polizas_frontend:
    ```bash
    cd polizas_front
    ```
2.  Instala las dependencias de Node:
    ```bash
    npm install
    ```
3.  Inicia el servidor de desarrollo:
    ```bash
    ng serve
    ```
4.  Abre tu navegador en: `http://localhost:4200`

---

**Jacqueline Robles**

---
