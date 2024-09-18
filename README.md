## APLICACIÓN ANDROID - TALLER 1



- INTRODUCCIÓN

Esta aplicación de Android permite al usuario cambiar el color de fondo de las diferentes pantallas a través de una pantalla de configuración.
El cambio de color se refleja de manera persistente en todas las pantallas de la aplicación,incluso cuando el usuario navega entre ellas.
El proyecto está desarrollado en Kotlin usando Android Studio.



- OBJETIVOS

Comprender el entorno de desarrollo Android Studio y sus componentes.
Diseñar y desarrollar interfaces de usuario interactivas.
Implementar el manejo de eventos en aplicaciones Android.
Realizar pruebas y depuración de aplicaciones Android.



- PARTES A REALIZAR

  1. Pantalla de Inicio:
Un saludo personalizado que cambia según la hora del día (Buenos días, Buenas tardes, Buenas noches).
Un botón que lleve a la pantalla de la actividad principal.

  2. Actividad Principal: 
Un campo de texto para ingresar el nombre del usuario.
Un botón para guardar el nombre ingresado.
Un TextView que muestre el nombre ingresado.
Un botón para navegar a una tercera pantalla.

  3. Pantalla de Configuración:
Opciones para cambiar el color del fondo de la aplicación.
Un botón para volver a la pantalla de inicio.



- REALIZACIÓN DEL EJERCICIO

Archivos XML:
     
pantalla_inicio.xml
Este archivo define el diseño de la pantalla de inicio de la aplicación.
Contiene un saludo que cambia dependiendo de la hora del día, y un botón que permite navegar a la actividad principal.

actividad_principal.xml
El archivo de diseño para la actividad principal de la aplicación.
Incluye un campo de texto para que el usuario ingrese su nombre, un botón para guardar el nombre y un botón que lleva a la pantalla de configuración.

pantalla_configuracion.xml
Define la interfaz de la pantalla de configuración.
Contiene un botón que cambia el color de fondo de la aplicación y otro botón que permite regresar a la pantalla de inicio.

Clases Kotlin:
  
PantallaInicio.kt
Esta clase maneja la lógica de la pantalla de inicio.
Muestra un saludo dinámico según la hora del día y permite al usuario navegar a la actividad principal.

ActividadPrincipal.kt
Gestiona la actividad principal, donde el usuario puede ingresar su nombre y ver un saludo personalizado.
También tiene un botón para navegar a la pantalla de configuración.

PantallaConfiguracion.kt
Maneja la pantalla de configuración. Aquí es donde el usuario puede cambiar el color de fondo. 
Este cambio se guarda de manera persistente y se refleja en todas las pantallas de la aplicación.



- INSTALACIÓN

Clona este repositorio en tu máquina local.
Abre el proyecto en Android Studio.
Ejecuta la aplicación en un emulador o dispositivo físico.



Link al repositorio: https://github.com/Samuu10/ProgramacionConcurrenteTaller1.git


PD: Me he equivocado en el título, he puesto Programación Concurrente en vez de Programación Dirigida Por Eventos :(
