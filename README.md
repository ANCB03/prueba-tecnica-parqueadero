
# Prueba Técnica Parqueadero - Nelumbo

En este README se va a explicar como ejecutar el proyecto ya sea de manera local o
de manera remota desde los enlaces de despliegue.




## Prerrequisitos

- Se debe tener una version de java 17 o superior.

- Se debe tener maven en el computador o utilizarlo por medio del entorno de desarrollo como por ejemplo IntelliJIDEA.

- Tener el puerto 8080 disponible ya que el proyecto se ejecuta en este por defecto.
## Ejecutar en local

**1** - Clonar el proyecto:

```bash
  https://github.com/ANCB03/prueba-tecnica-parqueadero.git
```

**2** - Abrir el proyecto en el entorno de desarrollo que uses.

**3** - Descargar las depenedencias que usa el proyecto en el pom(En IntelliJIDEA está el reload project de maven).


**4** - Ejecutar el siguiente comando de maven que permite que se generen los mappers y demás cosas necesarias:
```bash
  mvn clean install
```

**5** - Configurar variables de entorno, estas se encuentran en el siguiente archivo dentro del proyecto:
```bash
  src/main/resources/application.properties
```
En este caso dejaré aquí las credenciales de la base de datos que está alojada en RDS AWS:

`MYSQL_HOSTNAME` `parqueadero-1.czuqy2sgk6cr.us-east-2.rds.amazonaws.com`

`MYSQL_PORT`   `3306`

`MYSQL_DATABASE` `pruebap`

`MYSQL_USERNAME` `root`

`MYSQL_PASSWORD` `123456789root`

**6** - Ejecutar el proyecto.

**7** - Una vez ejecutado el proyecto este estará en el puerto 8080 y se podrán hacer peticiones desde esta base url:

```bash
  http://localhost:8080
```
**OPCIONAL** - Sí deseas probar la API email de manera local con la API parqueadero deberás editar el archivo a continuación ya que este se encuentra configurado con la API email desplegada:

```bash
  src/main/java/org/pruebatecnica/parqueadero/implement/CorreoImplement.java
```

dentro de la clase debes editar el constructor y quitar "https://email-latest.onrender.com" y colocar:
```bash
  http://localhost:8081
```
debería quedarte así:
```bash
  public CorreoImplement(WebClient.Builder webClientBuilder, RegistroRepository registroRepository, MessageUtil messageUtil) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081").build();
        this.registroRepository = registroRepository;
        this.messageUtil = messageUtil;
    }
```
## Nota

La aplicación precarga el usuario mencionado en la prueba por lo tanto se puede loguear con ese.
## Ejecutar desplegada

Sí quieres ejecutar la API parqueadero desplegada debes usar la siguiente url base:
```bash
  https://paqueadero-latest.onrender.com
```
tener en cuenta que puede demorarse la primera vez ya que es un servicio en el tier free de render y este se apaga si no se usa después de un tiempo. Por lo tanto puede demorar un poco en iniciar.
## Documentación Postman

Para acceder a la Documentación de postman debes ingresar a este link:
- [https://documenter.getpostman.com/view/24144423/2sAYQXnXsY](https://documenter.getpostman.com/view/24144423/2sAYQXnXsY)

También puedes consultar la documentación de la aplicación por SWAGGER con este link:

**Local:**
- [http://localhost:8080/swagger-ui/#/](http://localhost:8080/swagger-ui/#/)

**Desplegada:**
- [https://paqueadero-latest.onrender.com/swagger-ui/#/](https://paqueadero-latest.onrender.com/swagger-ui/#//)



## Autor

- [@ANCB03](https://www.github.com/ancb03)


