<p align="center">
  <img src="https://capsule-render.vercel.app/api?type=waving&height=150&section=header&color=0:0F172A,30:1E40AF,70:22C55E,100:16A34A" width="100%" alt=""/>
</p>

<div align="center">

# 🔐 Android Login — SQL Server

**App Android en Java con conexión directa a SQL Server** vía **jTDS / MS SQL JDBC**. Login + registro, lista para laboratorio y demos locales.

[![Java](https://img.shields.io/badge/Java-17-ED8B00?style=flat-square&logo=openjdk&logoColor=white)](https://openjdk.org)
[![Android](https://img.shields.io/badge/Android-SDK_34-3DDC84?style=flat-square&logo=android&logoColor=white)](https://developer.android.com)
[![SQL Server](https://img.shields.io/badge/SQL_Server-2019+-CC2927?style=flat-square&logo=microsoftsqlserver&logoColor=white)](https://www.microsoft.com/sql-server)
[![jTDS](https://img.shields.io/badge/Driver-jTDS_1.3.1-4479A1?style=flat-square)](http://jtds.sourceforge.net)
[![License](https://img.shields.io/badge/license-MIT-22C55E?style=flat-square)](#licencia)

</div>

---

## ✨ Características

- **Login / Registro** con validación y mensajes de error.
- **Conexión directa SQL Server** (jTDS) — ideal para intranet / prácticas. Para producción usa API REST (ver [`aplicativo-java`](../aplicativo-java)).
- **Config sin secretos en repo:** credenciales vía `local.properties` → `BuildConfig` (no hay IPs ni passwords hardcodeados).
- UI con Material Components y `ConstraintLayout`.

> ⚠️ **Nota de arquitectura:** la conexión directa desde el dispositivo solo es para demos en red local. En producción expón una API REST con JWT (ej. `proceso-nodes` o `aplicativo-java/api`).

## 🛠️ Stack

`Java 17` · `Android SDK 34 (min 32)` · `Gradle 8` · `jTDS 1.3.1` + `mssql-jdbc 12.6.3` (ambos incluidos en `app/libs/` para build offline)

## 📸 Capturas

| Login | Registro | Principal |
|---|---|---|
| ![Login](docs/screenshots/login.png) | ![Registro](docs/screenshots/register.png) | ![Main](docs/screenshots/main.png) |

> Si no ves las imágenes, abre `app/src/main/res/layout/` — los placeholders en `docs/screenshots/` se generan al hacer build.

## 🚀 Inicio rápido (sin exponer credenciales)

1. Clona y abre en **Android Studio Hedgehog+**.
2. Crea `local.properties` en la raíz (ya está en `.gitignore`):

```properties
# Ejemplo — ajusta a tu SQL Server local
DB_IP=TU_SERVIDOR:PUERTO
DB_USER=tu_usuario
DB_PASSWORD=tu_password
DB_NAME=Login
# También puedes definir sdk.dir si Android Studio no lo crea
# sdk.dir=C\:\\Users\\tu\\AppData\\Local\\Android\\Sdk
```

3. Habilita TCP/IP en SQL Server Configuration Manager y crea la BD `Login`.
4. **Run** en emulador o dispositivo (misma red Wi-Fi que el servidor SQL si usas IP local).

Sin `local.properties` el build usa placeholders `YOUR_SERVER_IP:PORT` y fallará al conectar — esperado.

## 🔒 Seguridad

- **Nunca** commitees `local.properties`. Verifica `.gitignore` lo excluye.
- Las credenciales van a `BuildConfig.DB_*` generadas en build, no en `ConnetionBD.java`.
- Usa `10.0.2.2` para emulador que apunta a `localhost` del host.

## 📂 Estructura

```
app/
 ├─ libs/               # jTDS + mssql-jdbc (offline)
 ├─ src/main/java/com/example/login/
 │   ├─ LoginActivity.java
 │   ├─ RegistrarActivity.java
 │   ├─ MainActivity.java
 │   └─ connetion/ConnetionBD.java  # lee BuildConfig
 └─ src/main/res/layout/ # login.xml, activity_main.xml
```

## 🔄 Alternativa recomendada

Para producción / Play Store usa la arquitectura del repo **[aplicativo-java](https://github.com/progamins/aplicativo-java)**: `Kotlin + Compose + API REST (JWT + refresh tokens) + Docker`. Migración simple: reemplaza `ConnetionBD` por Retrofit hacia `/api/auth`.

## Licencia

MIT — ver `LICENSE`.
