# Proyecto Registraduria Nacional
## Grupo 3
## Backend Seguridad


### Integrantes
- David Andres Cañas Suarez
- Jaime Andres Martinez Noguera
- Jimmi Antonio Calvo Hoyos
- Max Felipe Estupiñan Esguerra

![](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQsPQP-OK1qTBxrLNSrr8VS1ebp7mbEAaxGCpj_CIb8&s)

### Descripción
Desarrollo del módulo de seguridad mediante la implementacion de Spring Boot y con vinculación a una base de datos no relacion en MongoDB. Este modulo permite la creacion de perimisos, roles y usuarios. Los permisos consisten en permitir el acceso a los diferentes servisios de la plataforma. A los diferentes roles (Administrador, Jurado y Ciudadano), se les asignan los permisos previamente creados, finalmente, a cada usuario creado se le puede asignar un rol con sus permisos. Por defecto todo usuario creado tendra el rol Ciudadano.  

### Requisitos iniciales
1. Se requiere la creacion de una base de datos en MongoDB
2. Se deben ingresar las credenciales de la base de datos para la vincualación de la misma. En el archivo: src/main/resources/application.properties. 
3. se debe obtener algo como lo siguiente:

```Java
spring.data.mongodb.uri=mongodb+srv://dacanass:<password>@cluster0.thla4tw.mongodb.net/?retryWrites=true&w=majority
spring.data.mongodb.database=bd-autenticacion
server.error.include-message=always
```
### Requests
#### 1. Permisos
##### 1.1 Crear permiso
```Java
POST http://localhost:8080/permisos/
{
    "url": "http://localhost:8080/usuarios/:id",
    "nombre": "Editar usuario",
    "metodo": "PUT"
}
```
##### 1.2 Mostrar todos los permisos
```Java
GET http://localhost:8080/permisos/
```

##### 1.3 Mostrar permiso por id
```Java
GET http://localhost:8080/permisos/
```
##### 1.4 Editar permiso
```Java
PUT http://localhost:8080/permisos/:id
{
    "url": "http://localhost:8080/usuarios/:id",
    "nombre": "Editar usuario",
    "metodo": "PUT"
}
```
##### 1.4 Eliminar permiso
```Java
DELETE http://localhost:8080/permisos/:id
```
#### 2. Rol
##### 2.1 Crear rol
```Java
{
    "nombre": "Jurado",
    "permisos": [
        { 
            "id": "6325ecdfb92f6f5a08d3f08a"
        },
        { 
            "id": "6325ecf5b92f6f5a08d3f08b"
        }
    ]
}
```
##### 2.2 Consultar rol
```Java
GET http://localhost:8080/roles/:id
```
##### 2.3 Editar rol
```Java
PUT http://localhost:8080/roles/:id
{
	"nombre": "jurado"
    "permisos": [
        { 
            "id": "6325ecdfb92f6f5a08d3f08a"
        },
        { 
            "id": "6325ecf5b92f6f5a08d3f08b"
        },
        { 
            "id": "6325ed05b92f6f5a08d3f08c"
        },
        { 
            "id": "6325ed12b92f6f5a08d3f08d"
        }
    ]
}
```
##### 2.4 Eliminar rol
```Java
DELETE http://localhost:8080/roles/:id
```
#### 3. Usuarios
##### 3.1 Crear usuario
```Java
POST http://localhost:8080/usuarios/
{
    "seudonimo":"gatoknas",
    "correo":"example@example.com",
    "contrasena":"Qwerty",
    "rol":{"id": "6325f41a4ab0ca3cf9a49fde"} //id del rol
} 
```
##### 3.2 Ver usuarios
```Java
GET http://localhost:8080/usuarios/
```
##### 3.3 Ver usuario por id
```Java
GET http://localhost:8080/usuarios/:id
```
##### 3.4 Editar usuario
```Java
PUT http://localhost:8080/usuarios/:id
{
    "seudonimo":"gatoknas",
    "correo":"example@example.com",
    "contrasena":"Qwerty",
    "rol":{"id": "6325f41a4ab0ca3cf9a49fde"}
} 
```
##### 3.5 Eliminar usuario
```Java
DELETE http://localhost:8080/usuarios/:id
```
