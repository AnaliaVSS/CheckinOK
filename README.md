🧾 CheckinOK
CheckinOK es una aplicación desarrollada en Java (NetBeans IDE) que permite gestionar el registro de asistencia o inscripción de personas en distintas actividades, cursos o eventos. Su objetivo es simplificar el control de ingreso y mantener organizada la información de los participantes de forma rápida y segura.
🚀 Características principales ✅ Registro de participantes con datos personales (nombre, CI, curso, hora, etc.) 🕒 Control de horario de inscripción y asistencia. 🔍 Búsqueda rápida de personas registradas. 💾 Almacenamiento local de los datos. 🧰 Interfaz gráfica simple e intuitiva (Swing).
Tecnologías utilizadas Idioma: Java SE IDE: Apache NetBeans Control de versiones: Git y GitHub
Ejemplo de uso Ingresá el nombre, CI y curso del participante. Presioná Registrar para guardar la información. Visualice los registros en la tabla principal. Podés buscar o resumen de registros. esa es la master , persistenca permite el guardado de datos quiero crear el readme 
🏗️ La Estructura de CheckinOK: Un Vistazo Rápido
Para que CheckinOK funcione bien, está dividido en partes clave:
 
AGREGAMOS PERSISTENCIA: 
💾 La Persistencia en CheckinOK
En el caso específico de CheckinOK, la Persistencia es la función  que asegura que cada registro de asistencia capturado (el Nombre, la CI y la Hora) se almacene de forma permanente y esté disponible en el futuro. Cuando un usuario ingresa datos y presiona "Registrar", la capa de Persistencia toma la información del objeto de Java (que reside en la memoria volátil o RAM) y la escribe inmediatamente en una base de datos local no volátil. De esta manera, si la aplicación se cierra, se reinicia la computadora, o incluso si hay un fallo de energía, la Persistencia garantiza que los registros de los participantes no se pierdan y puedan ser recuperados por la aplicación al momento de reiniciarse.🌟 1. El Código Principal (La Rama main)
Imagina la rama main como el motor y el tablero de control del auto.

Contiene todo el código que ves: la pantalla de registro, los botones, la tabla donde aparecen los nombres, y la lógica básica de cómo funciona la aplicación.

Es la versión estable y lista para usar del programa.

💾 2. Persistencia

¿Qué es? Es una caja fuerte digital integrada a la aplicación.

¿Qué hace? Cada vez que presionas "Registrar", la Persistencia toma esos datos (nombre, CI, hora) y los guarda de forma segura en la memoria local de tu computadora (una pequeña base de datos).

Beneficio: Gracias a la Persistencia, aunque cierres el programa, apagues la PC o haya un corte de luz, la próxima vez que inicies CheckinOK, todos tus registros seguirán ahí. ¡Nunca se pierden!
