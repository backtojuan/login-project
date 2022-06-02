# Proyecto Software Final Seguridad
##### por: Salinas Lina, Sanchez Felipe, Valencia Juan José 

### Funcionalidades
- Login para usuarios con dos tipos de priviligeo: Administrador y usuario normal.
- Cifrado para contraseñas (PBKDF2)
- El usuario administrador puede consultar los usuarios existentes
- El usuario administrador puede eliminar un usuario existente
- El usuario administrador puede poner la contraseña de un usuario en blanco
- El usuario normal puede consultar su ultima fecha y hora de acceso
- El usuario normal puede cambiar su contraseña

### Tecnologias
- [Java] - Código fuente y API de seguridad
- [JavaFX] - Diseño de interfaz gráfica de usuario
- [PostgreSQL] - Base de datos

## Cómo se hizo?

1. Creación y diseño de las vistas con SceneBuilder.

```sh
Son archivos FXML.
Las vistas fueron el login, la vista del administrador y la vista del usuario.
El lenguaje que se utiliza para la gestión y creación de contenedores es JavaFX.
```

2. Creación de la base de datos en PostgreSQL

```sh
La tabla se llama users
Sus atributos son: El id del usuario, su nombre de usuario, su privilegio y su contraseña cifrada.
```

3. Implementación del cifrado de contraseñas y lógica del login
```sh
Se integraron los métodos de cifrado.
Se implemento la conexión entre el programa y la base de datos.
Se hicieron los controladores de las vistas. Además, se hicieron las funciones dentro de cada uno para cumplir con los requerimientos especificados.
Se manejan todas las excepciones posibles dentro del programa.
```

## Dificultades en el desarrollo del proyecto.

#### Verificar que los algoritmos de cifrado fueran acorde a lo que buscabamos.
    Ya que teniamos que cumplir con unas condiciones dadas, fue un reto realizar la búsqueda de algunos métodos que nos ayudarán al funcionamiento del programa usando el algoritmo solicitado y el API de seguridad de Java
    
## Conclusiones
En conclusión, es importante resaltar que con el desarrollo de un proyecto como este se puede evidenciar la importancia con la que interviene la seguridad en los sistemas que como profesionales diseñamos e implementamos para nuestros usuarios pues esto incluye dar privilegios a un usuario según sus necesidades, proteger los datos de estos usuarios y garantizar que estos se mantengan consistentes y protegidos. Por esto mismo se puede decir que la seguridad es transversal en todo un proyecto y que se debe mantener en todos los componentes de un sistema: lógica del modelo, interfaz, bases de datos, etc.
