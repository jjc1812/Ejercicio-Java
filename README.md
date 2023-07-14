# Ejercicio-Java
Para levantar el proyecto es necesario tener instalado:
- Java 8, con las variables de entorno JAVA_HOME y PATH apuntando a esa version.
- Git para descargar el repositorio desde github.
- Gradle 8, instalado en la carpeta Gradle y la variable de entorno PATH apuntando al /bin de la carpeta descargada de gradler.
- las extensiones de spring boot dashboard y spring boot tools en visual estudio code para correr el programa y el mode debbuger.
- Alternativamente se puede ejecutar este comando en la terminal para levantar el programa: & 'C:\Program Files\Eclipse Adoptium\jdk-8.0.345.1-hotspot\bin\java.exe' '-Dcom.sun.management.jmxremote' '-Dcom.sun.management.jmxremote.port=55011' '-Dcom.sun.management.jmxremote.authenticate=false' '-Dcom.sun.management.jmxremote.ssl=false' '-Dspring.jmx.enabled=true' '-Djava.rmi.server.hostname=localhost' '-Dspring.application.admin.enabled=true' '-Dspring.boot.project.name=user' '-cp' 'C:\Users\jcoronel\AppData\Local\Temp\cp_cd73mtfvat3j8ck9n1wgwymkv.jar' 'com.example.user.UserApplication'

Ejemplo de los curl del get y post:

GET:
curl --location --request GET 'http://localhost:8080/user/login' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic dXNlcjphZG1pbg==' \
--data-raw '{
    "id_user": "e5c6cf84-8860-4c00-91cd-22d3be28904e",
}'

POST:
curl --location 'http://localhost:8080/user/sign-up' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic dXNlcjphZG1pbg==' \
--data-raw '{
    "name": "Test",
    "email": "test@testssw.cl",
    "password": "a2asfGfdhnf4",
    "phone":
        {
            "number": 87650009,
            "citycode": 7,
            "contrycode": "25"
        }
}'

DLL para crear la tabla en la base de datos (MySQL en mi caso):
CREATE DATABASE `poo`;
CREATE TABLE `user` (
  `id_user` varchar(100) NOT NULL,
  `created` date NOT NULL,
  `last_login` date DEFAULT NULL,
  `token` varchar(256) NOT NULL,
  `is_active` int NOT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `phone_number` int DEFAULT NULL,
  `phone_city_code` int DEFAULT NULL,
  `phone_country_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;