# Proyecto Oompa Loompas 

A continuación se va a explicar las distintas herramientas y tecnologías usadas en el proyecto.

### Arquitectura
La arquitectura usada es la recomendada por Google, es decir: **MVVM**. Además también se separan las distintas capas de la aplicación para seguir una Clean Architecture y también se siguen los principios solid. 

### Herramientas
#### Para la capa de presentación:
- **ViewModel:** Encargado de la comunicación entre la capa de dominio y la capa de presentación.
- **Jetpack Compose:** Junto con todo su ecosistema para el desarrollo de las vistas.

#### Para la capa de dominio: 
- **Casos de uso:** Para implementar la lógica de negocio.
- **Mappers:** Necesario para poder mapear los datos desde la capa de Data.
- **Repositorio:** Abstracción del repositorio que contendrá toda la lógica encargada de la lógica de negocios.

#### Para la capa de datos:
- **Repositorio:** Implementación de repositorio que contendrá toda la lógica de negocio y tendrá acceso a abstracciones de una base de datos en local y un servicio en remoto.
- **LocalDataBase:** Usado para checar los datos provenientes del servicio en remoto y también para trabajar sobre estos datos. 
    - Se usará **ROOM** para poder persistir datos en una Base de Datos SQL y también poder realizar operaciones sobre ellos.
- **RemoteService:** Usado para obtener los datos del servicio en remoto. 
    - Se usará **Retrofit** para poder acceder al servicio en remoto. Para el manejo de error y adaptar la respuesta de Retrofit se hace uso de un Call Adapter, esto junto a una sealed class Resource permite adaptar el tipo de respuesta a los servicios de la API. 

#### Aspectos claves usado en toda la arquitectura del proyecto:

- Un aspecto importante es el uso de una sealed class Error Entity para el **"handler error"**. Esto permite no propagar una Exception propia de ROOM o de Retrofit al resto de capas ya que esto supondría que dichas capas conozcan las herramientas empleadas en otras y se estaría violando el principio de capas de una clean architecture. Además al ser una sealed class este tipo de errores será más fácil manejable en la capa de presentación y más legible para el programador.

- Otro aspecto es la **Comunicación entre la capa de presentación y la de dominio.** Para ello se hace uso de clase FlowUseCase que nos permitirá declarar casos de uso que trabajaran con flow para poder trabajar con vistas reactivas y también permitirán que el testeo sea más fácil.

- Como se ha mencionado antes **Resouce es una sealed class que permitirá propagar el resultado tanto si es un error como si es un resultado exitoso entre las distintas capas.**

- Para hacer un buen uso de Jetpack Compose y no realizar más recompositions de las necesarias se siguen varias recomendaciones. Como por ejemplo: usar wrappers para las listas, wrappers para los objetos provenientes de otras capas, uiState con variables de tipo __val__. Estas recomendaciones se pueden leer en  [este articulo](https://getstream.io/blog/jetpack-compose-guidelines/#aim-to-write-stable-classes)

- Uno de los principios solid seguido es la **Inversión de dependencias** y para realizar la inyección de dependencias se hace uso de **Dagger.** Este es uno de los principios en lo personal más importante dentro del ecosistema android.

- Otro principio solid usado el es **Principio de responsabilidad única** ya que por ejemplo los viewmodels solo se encargan de la comunicación entre la capa de presentación y la capa de dominio.

- **Navegación en jetpack compose:** Se implementa la navegación recomendada donde se tiene una sealed class para construir las distintas rutas de las vistas y una barra inferior para poder navegar.

#### Testing
El test es una de las herramientas que poco se usa pero es un gran aliado, en este proyecto se hacen test en todas las capas
- **Capa de datos:**
    - **API:** Lo normal es consumir datos de una API y los datos que devuelven pueden cambiar y no darnos cuenta hasta que falla. Con este tipo de test permite controlar el parseo de las llamadas a la API tanto teniendo una respuesta en local como realizando la llamada directamente a la API.
    - **Repositorio:** Permite testear las funcionalidades de la implementación del repositorio. Uno de los puntos claves del proyecto es recuperar datos de  la **API** y cachear estos mismos datos en la  **base de datos en loca**. Y al trabajar con abstracciones este tipo de test son realmente fáciles. 
- **Capa de presentación:**
    - **ViewModels:** El viewmodel es un componente clave para darle vida a las aplicaciones y por lo tanto un elemento con funcionalidades importantes. Por ello también se realizan test. En este proyecto solo se han realizado test al HomeViewModel y gracias a la forma en la que están implementados los UseCase el testeo es bastante rápido.

#### Mejoras
Hay mucho margen de mejora como en todos los proyectos android algunos de las mejoras que se puede aplicar al proyecto son:
- Detalle Oompa Loompa: Añadir un elemento visual para poder permitir al usuario ir hacia atrás.
- Search: Tal y como está implementado el proyecto añadir nuevos filtros es sencillo también se debería de permitir al usuario poder hacer un reset directo de todos los filtros con un solo click y también cada filtro por separado. La posibilidad de añadir a favoritos desde la vista de Search pero como está en el resto de las vistas aquí se omitió por falta de tiempo.
