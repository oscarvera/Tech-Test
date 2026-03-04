# Prueba técnica

## Descripción
Aplicación Android desarrollada en Kotlin que consume una API pública para mostrar un listado de productos, permite marcarlos como favoritos y persistirlos en local, y muestra un perfil de usuario con el número total de favoritos.

---
 
## Funcionalidades
- Listado de productos cargados desde red.
- Marcado y desmarcado de productos como favoritos.
- Persistencia de favoritos en base de datos local.
- Pantalla de favoritos con los productos guardados.
- Pantalla de perfil de usuario con contador de favoritos.
- Gestión de estados: loading / error / content.

---

## Arquitectura
La aplicación sigue una arquitectura **MVVM** combinada con el patrón **Repository** y una capa de **UseCases** para desacoplar la lógica de negocio de la capa de presentación.

- **UI (Compose)**: Pantallas y componentes sin lógica de negocio.
- **ViewModel**: Gestión de estado y coordinación entre UI y dominio.
- **Domain**: Modelos y casos de uso.
- **Data**: Acceso a red y persistencia local.

Esta estructura facilita el mantenimiento, la escalabilidad y el testing de la aplicación, a la vez que dado la sencillez de la aplicación no sobredimensiona su volumen.

---

## Stack tecnológico
- **Kotlin**
- **Coroutines + Flow**
- **Jetpack Compose**
- **Room** 
- **Retrofit** 
- **Koin** 
- **Navigation Compose**
- **JUnit4 + kotlinx-coroutines-test** (unit testing)

---

## Gestión de estado
Para la gestión de estados en la UI se utiliza un `UiState` simple que representa:
- Loading
- Success
- Error

Esto permite a las pantallas reaccionar de forma clara y predecible a los cambios de estado.

---

## Persistencia
Los productos marcados como favoritos se almacenan en una base de datos local mediante Room.  
La información persistida es la mínima necesaria para poder mostrar el listado de favoritos sin depender de la red.

---

## Testing
Se han incluido **tests unitarios** y **tests de integración** con el objetivo de validar el comportamiento de la aplicación en los puntos más críticos.

### Unit tests
Los tests unitarios se centran principalmente en los ViewModels, utilizando repositorios fake para aislar la lógica de negocio.  
Se validan escenarios como:
- Carga correcta de datos.
- Gestión de estados de éxito y error.
- Marcado y desmarcado de productos como favoritos.

### Tests de integración
Además, se han añadido tests de integración para comprobar el funcionamiento conjunto de Room y Repository.
Estos tests permiten verificar que la persistencia de favoritos funciona correctamente y que los datos se almacenan y recuperan como se espera.

<br/>
<br/>





