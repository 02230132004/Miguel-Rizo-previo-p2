# 📱 Series Explorer

Aplicación móvil Android desarrollada en Kotlin que permite explorar información sobre series de televisión, visualizar detalles completos, consultar episodios y acceder a datos del país de origen, integrando múltiples APIs y almacenamiento local.

---

## 🚀 Características principales

* 📺 Lista paginada de series
* 🔍 Detalle completo de cada serie
* 🎬 Visualización de episodios
* 🌍 Información del país (nombre y bandera)
* 💾 Persistencia local con Room
* ⚡ Caché con TTL (15 minutos)
* 📡 Consumo de APIs REST con Retrofit
* 🔄 Manejo de estados: Loading, Success, Error, Empty
* 📶 Soporte básico offline (offline-first)

---

## 🧱 Arquitectura

La aplicación implementa **MVVM (Model-View-ViewModel)** con separación clara de capas:

```
data/
 ├── local/        → Room (Entities, DAO, Database)
 ├── remote/       → Retrofit (Services, DTOs, Mappers)
 └── repository/   → Implementación de repositorios

domain/
 ├── model/        → Modelos de dominio
 └── repository/   → Interfaces de repositorio

ui/
 ├── list/         → Pantalla de lista + ViewModel
 └── detail/       → Pantalla de detalle + ViewModel

di/                → Inyección de dependencias (Hilt)
```

---

## 🛠️ Tecnologías utilizadas

* **Kotlin**
* **MVVM**
* **Hilt (Dependency Injection)**
* **Room (Persistencia local)**
* **Retrofit + OkHttp (Consumo de APIs)**
* **Coroutines + Flow**
* **StateFlow**
* **RecyclerView**
* **Navigation Component**

---

## 🌐 APIs utilizadas

### 1. TVMaze

* Base URL: https://api.tvmaze.com
* Endpoints:

  * `/shows?page={page}`
  * `/shows/{id}`
  * `/shows/{id}/episodes`

### 2. REST Countries

* Base URL: https://restcountries.com
* Endpoint:

  * `/v3.1/name/{name}?fields=name,cca2,flags`

---

## 🧠 Manejo de datos

* Se implementa un **Repository** como única fuente de verdad.
* Los datos se obtienen desde la API y se almacenan en Room.
* Se utiliza un **TTL de 15 minutos** para validar la frescura de los datos.
* En caso de no tener conexión, se muestran datos en caché.

---

## ⚠️ Manejo de errores

Se implementa una sealed class `AppError` para diferenciar errores:

* ❌ Sin conexión (NoInternet)
* ⏱️ Timeout
* 🔍 Error 404 (NotFound)
* 🔥 Error de servidor (ServerError)
* ❓ Error desconocido (Unknown)

---

## 📊 Estados de la UI

Cada pantalla maneja los siguientes estados:

* ⏳ Loading
* ✅ Success
* ❌ Error
* 📭 Empty

---

## 📸 Capturas de pantalla

Ubicadas en la carpeta:

```
/screenshots/
```

Se incluyen:

* Estado de carga
* Datos cargados
* Error simulado

---

## ⚙️ Instalación y ejecución

1.Clonar el repositorio:
 git clone https://github.com/02230132004/Miguel-Rizo-previo-p2.git
2.Abrir el proyecto en Android Studio.
3.Sincronizar Gradle automáticamente.
4.Ejecutar la aplicación en un emulador o dispositivo físico.

---

## 📌 Requisitos

* Android Studio Hedgehog o superior
* minSdk 24
* Conexión a internet (para primera carga)

---

## 🧪 Pruebas

* Para simular error: desactivar internet
* Para validar caché: abrir app varias veces dentro de 15 minutos
* Revisar Logcat para consumo de APIs

---

## 📁 Estructura del repositorio

```
SeriesExplorer/
├── app/
├── screenshots/
├── README.md
```


