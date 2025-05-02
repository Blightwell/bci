#👤 user-api (BCI - Evaluación Java)

API RESTful para la creación y gestión de usuarios, desarrollada con Spring Boot y Java.

Este servicio permite crear, consultar, actualizar y eliminar usuarios, cumpliendo con los requisitos de BCI indicados en "Ejercicio_JAVA 1.pdf".

Para mayor compatibilidad, abrir este README en bloc de notas de windows.

---

## 🚀 Tecnologías

- Java 17
- Spring Boot 3.4.5
- Spring Data JPA
- H2 Database (en memoria)
- JWT (Json Web Token)
- Spring Security
- Maven
- Jakarta Validation

---

## 📦 Requisitos

- JDK 17
- Maven 3.x

---

## ⚙️ Instalación y ejecución

Haciendo clone al repo de github:
git clone https://github.com/Blightwell/bci.git
cd user-api
mvn clean install
mvn spring-boot:run

O descargando el proyecto a local (git clone ya no es necesario):
cd user-api
mvn clean install
mvn spring-boot:run

-> El servicio REST quedará disponible en: http://localhost:8080

---

## 🧪 Acceder a la consola H2 para ver la data en memoria (OPCIONAL)
- URL: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:testdb
- Usuario: sa
- Contraseña: (dejar en blanco)

Una vez se ejecute el endpoint POST para crear un usuario, en la consola H2 se encontraran las tablas de USUARIO y TELEFONO, para verlas solamente correr:
SELECT * FROM USUARIO
o
SELECT * FROM TELEFONO

---

## 📘 Endpoints disponibles
| Método | Endpoint                | Descripción                          |
|--------|-------------------------|--------------------------------------|
| POST   | `/usuarios`             | Crear nuevo usuario                  |
| GET    | `/usuarios`             | Listar usuarios o buscar por correo  |
| PUT    | `/usuarios/{id}`        | Reemplazar usuario completo          |
| PATCH  | `/usuarios/{id}`        | Modificar parcialmente un usuario    |
| DELETE | `/usuarios/{id}`        | Eliminar usuario                     |

## 📥 Ejemplo de creación (POST /usuarios)

curl --location 'http://localhost:8080/usuarios' \
--header 'Content-Type: application/json' \
--data-raw '{
    "nombre": "Juan Rodríguez",
    "correo": "juan@rodriguez.org",
    "contraseña": "abc12345",
    "telefonos": [
        {
            "numero": "1234567",
            "codigoCiudad": "1",
            "codigoPais": "57"
        }
    ]
}'

### 🧾 Request
{
  "nombre": "Juan Rodríguez",
  "correo": "juan@rodriguez.org",
  "contraseña": "abc12345",
  "telefonos": [
    {
      "numero": "1234567",
      "codigoCiudad": "1",
      "codigoPais": "57"
    }
  ]
}

### ✅ Response (201 Created)
{
  "id": "9a4a4e2e-b60c-4a23-a35a-1c3f946bddeb",
  "creado": "2025-04-30T13:41:21",
  "modificado": "2025-04-30T13:41:21",
  "ultimoLogin": "2025-04-30T13:41:21",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "activo": true
}

### ❌ Formato de error esperado
{ "message": "El correo ya está registrado" }

O si hay errores de validación:

{ "message": ["La contraseña debe tener al menos 8 caracteres, letras y números"] }

### 🔐 Validaciones
- El campo correo debe tener formato válido de email.
- El campo contraseña debe tener al menos 8 caracteres, contener letras y números (regex configurable en user-api/src/main/resources/application-dev.yaml, password.regex).
- El correo debe ser único en base de datos.

### 🔐 Seguridad con JWT
Después de crear un usuario, se genera un token JWT que debe ser incluido en el header de todas las peticiones protegidas:

Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

### 🔐 Ejemplo: GET /usuarios con token
curl -X GET http://localhost:8080/usuarios \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."

Si no se incluye token:
{ "message": "Falta token de autorización" }

O es inválido:
{ "message": "Token inválido o expirado" }

---

## ✅ Extra
- JWT se genera automáticamente y se guarda en la entidad Usuario.
- Soporte para operaciones CRUD completas.
- Manejador global de errores (@ControllerAdvice) retorna JSON estructurado.
- Compatible con herramientas como Postman o Swagger.
- Se adjunta colección de Postman para pruebas del servicio REST en git.

---

## ⚙️ POSTMAN
Se agrega colección Postman en github que contiene los curl ya configurados para probar todos los endpoints del servicio.
En el endpoint POST /usuarios se agrega un Script para guardar el id y token como variable de colección, la cual es utilizada en el resto de los endpoint.

---

## 📥cURL de endpoints (incluidos en colección POSTMAN)
1. POST /usuarios (crear usuario)
curl --location 'http://localhost:8080/usuarios' \
--header 'Content-Type: application/json' \
--data-raw '{
    "nombre": "Juan Rodríguez",
    "correo": "juan@rodriguez.org",
    "contraseña": "abc12345",
    "telefonos": [
        {
            "numero": "1234567",
            "codigoCiudad": "1",
            "codigoPais": "57"
        }
    ]
}'

2. GET /usuarios (obtener usuarios, con token)
curl --location 'http://localhost:8080/usuarios' \
--header 'Authorization: Bearer {{jwtToken}}'

3. PUT /usuarios/{{userId}} (actualización completa de usuario, con token)
curl --location --request PUT 'http://localhost:8080/usuarios/{{userId}}' \
--header 'Authorization: Bearer {{jwtToken}}' \
--header 'Content-Type: application/json' \
--data-raw '{
    "nombre": "Juan Rodríguez",
    "correo": "juan@rodriguez.org",
    "contraseña": "abc12345",
    "telefonos": [
        {
            "numero": "1234567",
            "codigoCiudad": "1",
            "codigoPais": "57"
        }
    ]
}'

4. PATCH /usuarios/{{userId}} (actualización parcial del usuario, con token)
curl --location --request PATCH 'http://localhost:8080/usuarios/{{userId}}' \
--header 'Authorization: Bearer {{jwtToken}}' \
--header 'Content-Type: application/json' \
--data '{
    "nombre": "Juan Rodríguez",
    "contraseña": "abc12345",
    "telefonos": [
        {
            "numero": "1234567",
            "codigoCiudad": "1",
            "codigoPais": "57"
        }
    ]
}'


5. DELETE /usuarios/{{userId}} (eliminar usuario, con token, retorna 204 No Content en caso OK)
curl --location --request DELETE 'http://localhost:8080/usuarios/{{userId}}' \
--header 'Authorization: Bearer {{jwtToken}}'

---

## 🧠 Autor
- Nombre: Tomás Melgarejo
- RUT: 19.153.491-3
- Perfil: Software Engineer
- Email: tmelgare@emeal.nttdata.com
