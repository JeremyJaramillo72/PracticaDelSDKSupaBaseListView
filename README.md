# App Android de Gestión de Alumnos con Supabase

Aplicación nativa de Android desarrollada en Kotlin que se conecta a una base de datos en tiempo real de **Supabase** utilizando su SDK oficial (Postgrest-Kt). Esta aplicación permite seleccionar un nivel (semestre) y una materia específica, para posteriormente mostrar una lista detallada de los estudiantes inscritos con un diseño de interfaz personalizado utilizando `ListView`.

## 🚀 Características y Funcionalidades

- **Integración con Supabase:** Consulta asíncrona mediante corrutinas de Kotlin y el SDK de Supabase.
- **Componentes Nativos:** Diseño de contenedores basado en vistas puras (`LinearLayout`, `ConstraintLayout`, `ListView`) sin utilizar componentes avanzados como RecyclerView o Jetpack Compose, ciñéndose estrictamente a la rúbrica del proyecto.
- **Adapter Personalizado:** Implementación de un `ArrayAdapter` diseñado a medida para cargar:
  - Nombre completo ordenado alfabéticamente.
  - Fotografía dinámica en formato circular usando la librería **Glide**.
  - Correo electrónico y teléfono de contacto con sus respectivos íconos descriptivos.
- **Seguridad:** Las credenciales de Supabase están ofuscadas y cargadas a través de `local.properties` (no expuestas en el código fuente).

---

## 📸 Capturas de Pantalla

*(Nota: Reemplazar estas imágenes con las fotos reales de la aplicación funcionando una vez subidas a tu repositorio)*

| Interfaz Principal | Lista de Alumnos Cargada |
|:---:|:---:|
| <img src="<img width="1031" height="1032" alt="Captura de pantalla 2026-06-10 193336" src="https://github.com/user-attachments/assets/fb9aea04-f79b-4a46-958f-4d14de8df3cf" />" width="250"/> |
<img src="<img width="1918" height="1023" alt="Captura de pantalla 2026-06-10 193319" src="https://github.com/userattachments/assets/c85e63c9-841b-4901-97a1-0e58a36b401e" />" width="250"/> |

---

## 🛠️ Tecnologías y Librerías

- **Lenguaje:** Kotlin
- **Base de Datos:** Supabase (PostgreSQL) 
- **SDK Supabase:** `io.github.jan-tennert.supabase:postgrest-kt`
- **Carga de Imágenes:** [Glide](https://github.com/bumptech/glide) (Transformación `CircleCrop`)
- **Gestor de JSON:** `kotlinx.serialization`
- **IDE:** Android Studio

---

## ⚙️ Instrucciones de Instalación y Configuración

Para clonar y correr este proyecto en tu entorno de desarrollo de forma local, sigue cuidadosamente las instrucciones a continuación.

### Requisitos previos
- Android Studio (versión Hedgehog, Iguana, Jellyfish o superior).
- JDK 17 o superior.
- Una cuenta y un proyecto configurado en Supabase con las tablas `materias` y `alumnos`.

### Pasos de Configuración

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/TU_USUARIO/TU_REPOSITORIO.git
   ```

2. **Abrir en Android Studio:**
   Abre Android Studio y selecciona `File > Open`, luego busca la carpeta del proyecto clonado.

3. **Configurar Credenciales de Supabase:**
   Por motivos de seguridad, las credenciales (URL y API Key) de Supabase no están incluidas en el repositorio. Debes crear un archivo en el directorio raíz del proyecto llamado `local.properties` y agregar las siguientes líneas con tus datos reales:

   ```properties
   # Archivo local.properties
   sdk.dir=C\:\\Users\\TU_USUARIO\\AppData\\Local\\Android\\Sdk
   SUPABASE_URL=https://TU_PROYECTO.supabase.co
   SUPABASE_KEY=TU_ANON_KEY_DE_SUPABASE
   ```

4. **Sincronizar Gradle:**
   Haz clic en *“Sync Project with Gradle Files”* (el ícono del elefante) en la barra superior de Android Studio para instalar las librerías de Glide, Supabase SDK y Serialización.

5. **Ejecutar en el Emulador/Dispositivo Físico:**
   Selecciona tu dispositivo (asegúrate de que tenga conexión a internet activa) y presiona el botón de **Run (Shift + F10)**.

---

## 📝 Arquitectura de Datos (Modelos)

El proyecto incluye la siguiente estructura principal de datos:

```kotlin
@Serializable
data class Alumno(
    val id: Int,
    val nombres: String,
    val correo: String,
    val telefono: String,
    val foto: String
)
```

## 👨‍💻 Autor

- **Luis Jeremy Jaramillo Acosta**
- Estudiante de la UTEQ
