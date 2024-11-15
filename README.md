---

# Proyecto de un Catálogo de Libros con la API Gutendex

Este proyecto utiliza Spring Boot y la API de Gutendex para construir una aplicación acerca de un catálogo de libros. Permite buscar libros por título y almacenar información de ellos, incluyendo sus autores y los idiomas. Utiliza una base de datos MySQL para el almacenamiento de datos.

## Características

- **Buscar libros por título:** Busca en la API de Gutendex y guarda los resultados en la base de datos.
- **Listar libros registrados:** Muestra todos los libros almacenados en la base de datos.
- **Listar autores registrados:** Muestra todos los autores registrados en la base de datos.
- **Buscar autores vivos en un año específico:** Lista los autores que estaban vivos en el año que se especifique.
- **Filtrar libros por idioma:** Lista los libros por idioma.

## Configuración

### Prerrequisitos

- **Java 17**
- **Maven 3.9.9**
- **MySQL** 

### Configuración de la Base de Datos

Configura las cosas necesarias de la base de datos en el archivo `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gutendex_db
spring.datasource.username=YOUR_DATABASE_USERNAME
spring.datasource.password=YOUR_DATABASE_PASSWORD
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Dependencias Principales

- **Spring Boot 3.2.0**
- **Spring Data JPA** para la comunicación con la base de datos.
- **Gutendex API** para obtener información sobre libros.
- **MySQL Connector** para la conexión con MySQL.

## Instalación

1. Clona el repositorio:
   ```bash
   git clone https://github.com/usuario/proyecto-literatura.git
   ```

2. Accede al directorio del proyecto:
   ```bash
   cd proyecto-literatura
   ```

3. Instala las dependencias usando Maven:
   ```bash
   mvn install
   ```

4. Configura tu base de datos como se indica en la sección de configuración.

5. Ejecuta la aplicación:
   ```bash
   mvn spring-boot:run
   ```

## Uso

Al ejecutar la aplicación, se abrirá una interfaz donde podrás seleccionar entre varias opciones:

1. **Buscar libro por título**: Busca y guarda libros por título.
2. **Listar libros registrados**: Muestra todos los libros en la base de datos.
3. **Listar autores registrados**: Muestra todos los autores en la base de datos.
4. **Listar autores vivos en un año específico**: Muestra autores vivos en el año ingresado.
5. **Listar libros por idioma**: Filtra libros por idioma.

## Licencia

Este proyecto está licenciado bajo la Licencia Apache 2.0. Para más detalles, consulta el archivo LICENSE en el repositorio.

---


