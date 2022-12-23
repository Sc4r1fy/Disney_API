### Indice
1. [Sobre este proyecto](#sobre-el-proyecto-)
2. [Herramientas usadas](#construido-con-%EF%B8%8F)
3. [Sobre el uso de la API](#comenzando-)
4. [Documentación](#documentacion)


## Sobre Este Proyecto 📖

El proyecto consiste en dejar al alcance de los clientes todo sobre el mundo de Disney.
El mismo podrá acceder a un catálogo con las películas/series históricas y además podrá
saber cosas relacionadas a los personajes de este universo.

El manejo/consumo de la API dependerá del rol de la persona logueada, dichos roles serán:

* Usuario común o cliente  (ROLE_USER)
* Admin o manager (ROLE_ADMIN , ROLE_MANAGER)

Roles :
- Como usuario podrás consultar todas las películas/series y personajes que hay en la base de datos.
- Como manager podrás gestionar las entidades que hay en la BD.
- Como admin podrás gestionar tanto los usuarios del sistema como las entidades de la BD.

## Herramientas usadas 🛠️

Se utilizaron las siguientes tecnologías para el proyecto:

* [Maven](https://maven.apache.org/)
* [MySQL](https://www.mysql.com/)
* [Spring](https://spring.io/)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
* [Spring Security](https://spring.io/projects/spring-security)


## Sobre el uso de la API 🚀

Para empezar a usar esta API  debes :

* Tener Java 11
* Clonar el proyecto desde github
* Instalar las dependecias en el pom.xml
* Descomentar las lineas comentadas en el archivo "DisneyApiV1Application.java" (Estas van a generar los roles y usuarios administradores cuando se corra por primera vez la aplicación)
* Correr el proyecto.

##Documentacion :pushpin:

Para más detalles del uso de la API :
https://documenter.getpostman.com/view/20177316/VUxLwUCg
