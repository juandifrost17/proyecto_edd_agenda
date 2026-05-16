# Agenda de Contactos - Proyecto de Estructura de Datos 2P

## Descripción General

Este proyecto consiste en una aplicación de escritorio desarrollada en **Java** para la gestión de una agenda de contactos. Permite registrar, buscar, mostrar, eliminar, cargar y exportar contactos utilizando estructuras de datos implementadas como parte del desarrollo académico de la asignatura **Estructura de Datos**.

La aplicación puede ejecutarse en dos modalidades:

- **Modo consola**, mediante un menú textual.
- **Modo gráfico**, mediante una interfaz de escritorio desarrollada con **JavaFX**.

El sistema trabaja con archivos **CSV** para la carga y exportación de contactos.

## Objetivo del Proyecto

El objetivo principal del proyecto es aplicar estructuras de datos en un caso práctico de gestión de información. Para ello, se implementa una agenda de contactos que permite realizar búsquedas eficientes por prefijo y organizar los resultados según criterios definidos en el sistema.

Entre las funcionalidades principales se encuentran:

- Registro de nuevos contactos.
- Búsqueda de contactos por nombre, apellido o apodo.
- Visualización de contactos registrados.
- Eliminación de contactos.
- Carga de contactos desde archivo CSV.
- Exportación de contactos a archivo CSV.
- Uso compartido de la lógica de negocio entre consola e interfaz gráfica.

## Tecnologías Utilizadas

- Java
- JavaFX
- Programación orientada a objetos
- Archivos CSV
- Estructuras de datos personalizadas:
  - Árbol de prefijos
  - Heap genérico
- IntelliJ IDEA

## Arquitectura General

El proyecto separa la lógica principal de la interfaz de usuario. La clase `Agenda` funciona como punto central para gestionar los contactos, mientras que las clases de estructuras de datos se encargan de organizar y buscar la información.

```text
Usuario
│
├── Modo Consola
│   └── MenuAgenda
│
└── Interfaz Gráfica JavaFX
    └── AppJavaFX / Vistas
        │
        └── Agenda
            ├── APrefijo
            ├── Heap
            ├── CargadorDatos
            └── ServicioExportacion
```

## Estructura del Repositorio

```text
proyecto_edd_agenda/
│
├── README.md
│
├── app/
│   ├── ListaContacto.iml
│   ├── contactos.csv
│   │
│   ├── src/
│   │   ├── Interfaz/
│   │   │   ├── AppJavaFX.java
│   │   │   ├── VistaBienvenida.java
│   │   │   ├── VistaBusqueda.java
│   │   │   ├── VistaCarga.java
│   │   │   ├── VistaEliminacion.java
│   │   │   ├── VistaExportacion.java
│   │   │   ├── VistaMostrar.java
│   │   │   └── VistaRegistro.java
│   │   │
│   │   └── Proyecto/
│   │       ├── APrefijo.java
│   │       ├── Agenda.java
│   │       ├── CargadorDatos.java
│   │       ├── ComparadorAlfabetico.java
│   │       ├── ComparadorFrecuencia.java
│   │       ├── Contacto.java
│   │       ├── GestorAgenda.java
│   │       ├── Heap.java
│   │       ├── MenuAgenda.java
│   │       ├── PantallaAgenda.java
│   │       └── ServicioExportacion.java
│   │
│   └── out/
│
├── csv/
│   └── contactos.csv
│
└── docs/
    └── Proyecto P2 - Estructura de Datos.pdf
```

## Principales Clases del Proyecto

| Clase | Descripción |
|---|---|
| `PantallaAgenda` | Clase principal que permite elegir entre modo consola e interfaz gráfica. |
| `Agenda` | Gestiona las operaciones principales sobre los contactos. |
| `Contacto` | Representa la información de cada contacto. |
| `APrefijo` | Implementa la estructura para realizar búsquedas por prefijo. |
| `Heap` | Estructura utilizada para ordenar resultados según prioridad. |
| `CargadorDatos` | Permite cargar y exportar contactos desde archivos CSV. |
| `ServicioExportacion` | Apoya el proceso de exportación de contactos. |
| `MenuAgenda` | Controla la interacción del usuario en modo consola. |
| `AppJavaFX` | Inicializa la interfaz gráfica del sistema. |

## Requisitos Previos

Para ejecutar el proyecto se necesita:

- JDK 16 o superior.
- JavaFX SDK instalado.
- IntelliJ IDEA u otro IDE compatible con Java.
- Git, en caso de clonar el repositorio desde GitHub.

## Instalación

Clonar el repositorio:

```bash
git clone https://github.com/juandifrost17/proyecto_edd_agenda.git
```

Ingresar al proyecto:

```bash
cd proyecto_edd_agenda
```

La aplicación se encuentra dentro de la carpeta:

```bash
app/
```

## Ejecución del Proyecto

La clase principal del proyecto es:

```text
Proyecto.PantallaAgenda
```

Al ejecutar esta clase, el sistema permite seleccionar el modo de uso:

```text
1. Modo Consola
2. Interfaz Gráfica
```

### Ejecución en IntelliJ IDEA

1. Abrir el proyecto en IntelliJ IDEA.
2. Configurar un JDK compatible.
3. Agregar JavaFX SDK como librería del proyecto.
4. Configurar las opciones de VM para JavaFX:

```bash
--module-path /ruta/al/javafx-sdk/lib --add-modules javafx.controls
```

5. Ejecutar la clase:

```text
Proyecto.PantallaAgenda
```

### Ejecución desde Terminal

Desde la carpeta `app`:

```bash
cd app
```

Compilar el proyecto:

```bash
mkdir -p out/classes
javac --module-path "$PATH_TO_FX" --add-modules javafx.controls -d out/classes $(find src -name "*.java")
```

Ejecutar la aplicación:

```bash
java --module-path "$PATH_TO_FX" --add-modules javafx.controls -cp out/classes Proyecto.PantallaAgenda
```

En este caso, `PATH_TO_FX` debe apuntar a la carpeta `lib` del JavaFX SDK.

## Funcionalidades Principales

### Modo Consola

El modo consola permite interactuar con la agenda mediante un menú textual con las siguientes opciones:

```text
1. Mostrar Contactos
2. Buscar Contactos
3. Registrar Contactos
4. Eliminar Contactos
5. Cargar CSV
6. Exportar Contactos
0. Salir
```

### Interfaz Gráfica

La interfaz gráfica desarrollada con JavaFX incluye vistas para:

- Mostrar contactos.
- Buscar contactos.
- Registrar contactos.
- Eliminar contactos.
- Cargar contactos desde CSV.
- Exportar contactos a CSV.

## Manejo de Archivos CSV

El proyecto utiliza archivos CSV para importar y exportar contactos.

El formato esperado es:

```csv
Nombre,Apellido,Apodo,TelefonoMovil,TelefonoConvencional,Correo
```

Ejemplo:

```csv
Carlos,Pérez,Luapodo,0965432109,025678901,carlos.perez@email.com
```

El repositorio incluye archivos CSV de ejemplo en:

```text
app/contactos.csv
csv/contactos.csv
```

## Video Demostrativo

Puedes consultar el video demostrativo del proyecto en el siguiente enlace:

[Ver video en Google Drive](https://drive.google.com/file/d/1pkAjwHis2M52pK_sd8N4e11F7yS_xJBv/view?usp=drive_link)

## Integrantes del Grupo

- Karel González
- Justin Soledispa
- Juan Diego Sotomayor
