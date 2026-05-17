# API de Gestión Bancaria — Santiago Tabares - Cristian Lopera

API REST desarrollada con **Spring Boot** para la gestión de información de un banco: clientes, cuentas, movimientos de dinero, transferencias y préstamos. El proyecto sigue una **arquitectura hexagonal** (dominio, aplicación e infraestructura) y persiste los datos en **MySQL**.

---

## Tabla de contenidos

1. [¿Qué hace esta API?](#qué-hace-esta-api)
2. [¿Cómo funciona?](#cómo-funciona)
3. [Requisitos para iniciar](#requisitos-para-iniciar)
4. [Configuración e inicio](#configuración-e-inicio)
5. [Formato de respuestas](#formato-de-respuestas)
6. [Endpoints](#endpoints)
7. [Reglas de negocio relevantes](#reglas-de-negocio-relevantes)
8. [Estructura del proyecto](#estructura-del-proyecto)
9. [Funcionalidades del dominio no expuestas por HTTP](#funcionalidades-del-dominio-no-expuestas-por-http)

---

## ¿Qué hace esta API?

Permite operar, mediante HTTP/JSON, los procesos principales de un banco:

| Área | Operaciones disponibles |
|------|-------------------------|
| **Clientes** | Registrar personas naturales y empresas, consultar datos y actualizar contacto |
| **Cuentas** | Abrir cuentas, depositar, retirar, consultar saldo, listar cuentas por cliente y bloquear cuentas |
| **Transferencias** | Transferencias internas inmediatas, transferencias a terceros con aprobación, aprobar/rechazar y consultar historial |
| **Préstamos** | Crear solicitudes, aprobar, rechazar, desembolsar y consultar estado o listado por cliente |
| **Monitoreo** | Verificar que el servicio esté activo (`/actuator/health`) |

Al arrancar, la aplicación crea automáticamente los productos bancarios base: **AHORROS**, **CORRIENTE** y **NOMINA**.

---

## ¿Cómo funciona?

### Arquitectura por capas

```
Cliente HTTP (Postman, navegador, etc.)
        │
        ▼
┌───────────────────────────────────────┐
│  application (controllers, DTOs,      │
│  casos de uso @Service)                 │
└───────────────────────────────────────┘
        │
        ▼
┌───────────────────────────────────────┐
│  domain (modelos, reglas de negocio,  │
│  puertos / servicios de dominio)      │
└───────────────────────────────────────┘
        │
        ▼
┌───────────────────────────────────────┐
│  infrastructure (JPA, adaptadores,  │
│  configuración Spring, seguridad)     │
└───────────────────────────────────────┘
        │
        ▼
      MySQL (banco_db)
```

### Flujo de una petición

1. El **controlador REST** recibe la petición y valida el formato (JSON en el cuerpo cuando aplica).
2. Un **caso de uso** de la capa de aplicación delega la operación al **puerto** correspondiente.
3. El **servicio de dominio** aplica las reglas de negocio (saldo suficiente, cliente activo, estados de préstamo, etc.).
4. El **adaptador de persistencia** guarda o consulta la información en MySQL mediante JPA/Hibernate.
5. La respuesta se envuelve en un objeto **`ApiResponse`** uniforme.

### Seguridad

Spring Security está incluido, pero configurado para **permitir todas las rutas** en desarrollo (sin autenticación obligatoria). No es apto para producción sin endurecer la configuración.

### Manejo de errores

- Errores de negocio (`BusinessException`): HTTP **400** con mensaje descriptivo.
- Errores inesperados: HTTP **500**.

---

## Requisitos para iniciar

| Requisito | Detalle |
|-----------|---------|
| **Java** | 17 o superior |
| **Maven** | Opcional si usas el wrapper incluido (`mvnw.cmd`) |
| **MySQL** | Recomendado vía **XAMPP**, puerto `3306` |
| **Base de datos** | `banco_db` (se crea automáticamente si no existe) |

### Credenciales por defecto (XAMPP)

Configuradas en `santiagotabares/src/main/resources/application.properties`:

- **Usuario:** `root`
- **Contraseña:** *(vacía)*
- **URL:** `jdbc:mysql://localhost:3306/banco_db`

Si tu MySQL usa otra contraseña, actualiza ese archivo antes de iniciar.

---

## Configuración e inicio

### 1. Iniciar MySQL

Abre el panel de **XAMPP** y activa el servicio **MySQL**.

### 2. Ejecutar la API

Desde la carpeta del módulo Maven:

```powershell
cd santiagotabares
.\mvnw.cmd spring-boot:run
```

**Alternativa:** ejecutar la clase `app.SantiagotabaresApplication` desde el IDE.

### 3. Verificar que está en línea

```http
GET http://localhost:8080/actuator/health
```

La API escucha por defecto en el puerto **8080**.

**URL base de los endpoints:** `http://localhost:8080`

Para peticiones con cuerpo JSON, enviar el header: `Content-Type: application/json`

---

## Formato de respuestas

Todas las respuestas de los controladores usan la envoltura `ApiResponse`:

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `success` | boolean | `true` si la operación fue exitosa |
| `message` | string | Mensaje descriptivo |
| `data` | objeto / lista / null | Datos devueltos (según el endpoint) |

---

## Endpoints

### Sistema — Monitoreo

#### `GET /actuator/health`

**Retorna:** estado del servicio (`status`: `UP` o `DOWN`) y grupos de salud disponibles. No requiere cuerpo.

---

### Clientes — `/api/clients`

#### `POST /api/clients/natural-person`

**Debe llevar:** cuerpo JSON con los datos de registro de una persona natural (identificación, nombre, fecha de nacimiento y, opcionalmente, datos de contacto).

**Retorna:** confirmación de creación (`data` en `null`). HTTP **201**.

---

#### `POST /api/clients/company`

**Debe llevar:** cuerpo JSON con los datos de registro de una empresa (NIT, razón social y, opcionalmente, datos de contacto).

**Retorna:** confirmación de creación (`data` en `null`). HTTP **201**.

---

#### `GET /api/clients/{clientId}`

**Debe llevar:** `clientId` en la URL (para persona natural es la cédula; para empresa es el NIT).

**Retorna:** objeto `ClientResponse` en `data` con: `id`, `name`, `type` (`NATURAL_PERSON` o `COMPANY`), `email`, `phone`, `address`, `status`.

---

#### `PUT /api/clients/{clientId}/contact`

**Debe llevar:** `clientId` en la URL y cuerpo JSON con email, teléfono y dirección actualizados.

**Retorna:** confirmación de actualización (`data` en `null`).

---

### Cuentas bancarias — `/api/accounts`

#### `POST /api/accounts`

**Debe llevar:** cuerpo JSON con número de cuenta, identificador del cliente, código de producto bancario y moneda.

**Retorna:** confirmación de creación (`data` en `null`). HTTP **201**.

---

#### `POST /api/accounts/{accountNumber}/deposit`

**Debe llevar:** `accountNumber` en la URL y cuerpo JSON con el monto a depositar.

**Retorna:** confirmación del depósito (`data` en `null`).

---

#### `POST /api/accounts/{accountNumber}/withdraw`

**Debe llevar:** `accountNumber` en la URL y cuerpo JSON con el monto a retirar.

**Retorna:** confirmación del retiro (`data` en `null`).

---

#### `GET /api/accounts/{accountNumber}/balance`

**Debe llevar:** `accountNumber` en la URL.

**Retorna:** saldo actual de la cuenta (`data` como valor numérico).

---

#### `PATCH /api/accounts/{accountNumber}/block`

**Debe llevar:** `accountNumber` en la URL. Sin cuerpo.

**Retorna:** confirmación de bloqueo (`data` en `null`).

---

#### `GET /api/accounts/client/{clientId}`

**Debe llevar:** `clientId` en la URL.

**Retorna:** lista de `BankAccountResponse` en `data`, cada uno con: `accountNumber`, `clientId`, `balance`, `status`, `currency`.

---

### Transferencias — `/api/transfers`

#### `POST /api/transfers/internal`

**Debe llevar:** cuerpo JSON con cuenta origen, cuenta destino y monto. La transferencia se **ejecuta de inmediato**.

**Retorna:** confirmación (`data` en `null`). HTTP **201**.

---

#### `POST /api/transfers/third-party`

**Debe llevar:** cuerpo JSON con cuenta origen, cuenta destino y monto. La transferencia queda **pendiente de aprobación**.

**Retorna:** confirmación de creación pendiente (`data` en `null`). HTTP **201**.

---

#### `PATCH /api/transfers/{transferId}/approve`

**Debe llevar:** `transferId` en la URL (identificador UUID de la transferencia). Sin cuerpo.

**Retorna:** confirmación de aprobación y ejecución (`data` en `null`).

---

#### `PATCH /api/transfers/{transferId}/reject`

**Debe llevar:** `transferId` en la URL. Sin cuerpo.

**Retorna:** confirmación de rechazo (`data` en `null`).

---

#### `GET /api/transfers/account/{accountNumber}`

**Debe llevar:** `accountNumber` en la URL.

**Retorna:** lista de `TransferResponse` en `data`, cada uno con: `id`, `originAccount`, `destinationAccount`, `amount`, `status`, `createdAt`.

---

### Préstamos — `/api/loans`

#### `POST /api/loans`

**Debe llevar:** cuerpo JSON con identificador del préstamo e identificador del cliente.

**Retorna:** confirmación de solicitud creada (`data` en `null`). HTTP **201**.

---

#### `PATCH /api/loans/{loanId}/approve`

**Debe llevar:** `loanId` en la URL y cuerpo JSON con el monto aprobado.

**Retorna:** confirmación de aprobación (`data` en `null`).

---

#### `PATCH /api/loans/{loanId}/reject`

**Debe llevar:** `loanId` en la URL. Sin cuerpo.

**Retorna:** confirmación de rechazo (`data` en `null`).

---

#### `PATCH /api/loans/{loanId}/disburse?accountNumber={accountNumber}`

**Debe llevar:** `loanId` en la URL y parámetro de consulta `accountNumber` (cuenta destino del desembolso). Sin cuerpo.

**Retorna:** confirmación de desembolso (`data` en `null`).

---

#### `GET /api/loans/client/{clientId}`

**Debe llevar:** `clientId` en la URL.

**Retorna:** lista de `LoanResponse` en `data`, cada uno con: `id`, `clientId`, `approvedAmount`, `status`.

---

#### `GET /api/loans/{loanId}/status`

**Debe llevar:** `loanId` en la URL.

**Retorna:** estado actual del préstamo (`data` como texto del enum, por ejemplo `UNDER_REVIEW`, `APPROVED`, `DISBURSED`).

---

## Reglas de negocio relevantes

- La persona natural debe tener **18 años o más** al registrarse.
- El email de contacto debe contener el carácter **`@`**.
- Solo se abren cuentas para clientes **activos**.
- Los productos bancarios válidos al crear cuenta son los precargados: **AHORROS**, **CORRIENTE**, **NOMINA**.
- No se puede retirar ni transferir más dinero del **saldo disponible**.
- Las cuentas **bloqueadas** no permiten depósitos ni retiros.
- Las transferencias a terceros requieren **aprobación** antes de debitar fondos.
- Un préstamo solo se **desembolsa** si está previamente **aprobado**.

---

## Estructura del proyecto

```
construcciondesoftware2SantiagoTabares/
├── README.md
└── santiagotabares/                    # Módulo Spring Boot
    ├── pom.xml
    ├── mvnw / mvnw.cmd
    └── src/main/java/
        ├── app/                          # Punto de entrada
        ├── application/                  # Controllers, DTOs, casos de uso
        ├── domain/                       # Modelos, puertos y servicios
        └── infrastructure/               # JPA, adaptadores, configuración
```

### Entidades principales del dominio

`Client`, `NaturalPerson`, `CompanyClient`, `BankAccount`, `BankProduct`, `Transfer`, `Loan`, `User`, `OperationLog`

### Estados (enums) usados en la API

| Enum | Valores relevantes |
|------|-------------------|
| `AccountStatus` | `ACTIVE`, `BLOCKED`, `CANCELLED` |
| `TransferStatus` | `PENDING`, `WAITING_FOR_APPROVAL`, `APPROVED`, `EXECUTED`, `REJECTED`, `EXPIRED` |
| `LoanStatus` | `UNDER_REVIEW`, `APPROVED`, `REJECTED`, `DISBURSED`, `IN_ARREARS`, `CANCELLED` |
| `UserStatus` | `ACTIVE`, `INACTIVE`, `BLOCKED` |

---

## Funcionalidades del dominio no expuestas por HTTP

El núcleo del dominio incluye servicios adicionales que **aún no tienen controlador REST** (no aparecen en la tabla de endpoints):

- Gestión de usuarios del sistema (crear, activar, bloquear, asignar permisos)
- Pagos masivos
- Consulta de logs de operación / auditoría
- Expiración automática de transferencias
- Consulta de operaciones por cuenta (más allá del historial de transferencias)

Estos pueden incorporarse en versiones futuras de la API.

---

## Tecnologías

- Java 17
- Spring Boot 4.0.2
- Spring Data JPA / Hibernate
- MySQL
- Spring Security (modo desarrollo abierto)
- Spring Actuator
- Lombok

---

*Proyecto académico — Construcción de Software 2*
