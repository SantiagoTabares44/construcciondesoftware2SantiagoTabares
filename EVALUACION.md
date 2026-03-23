# EVALUACIÓN - construcciondesoftware2SantiagoTabares

## Información General
- **Estudiante(s):** Santiago Tabares (SantiagoTabares44)
- **Rama evaluada:** develop
- **Fecha de evaluación:** 2026-03-23

---

## Tabla de Calificación

| # | Criterio | Peso | Puntaje (1–5) | Nota ponderada |
|---|---|---|---|---|
| 1 | Modelado de dominio | 25% | 4 | 1.00 |
| 2 | Relaciones entre entidades | 15% | 4 | 0.60 |
| 3 | Uso de Enums | 15% | 3 | 0.45 |
| 4 | Manejo de estados | 5% | 4 | 0.20 |
| 5 | Tipos de datos | 5% | 3 | 0.15 |
| 6 | Separación Usuario vs Cliente | 10% | 3 | 0.30 |
| 7 | Bitácora | 5% | 4 | 0.20 |
| 8 | Reglas básicas de negocio | 5% | 1 | 0.05 |
| 9 | Estructura del proyecto | 10% | 4 | 0.40 |
| 10 | Repositorio | 10% | 3 | 0.30 |
| **TOTAL** | | **100%** | | **3.65** |

## Penalizaciones
- Ninguna (código en inglés, nombres correctos).

## Bonus
- +2: Herencia correcta (`Client` → `CompanyClient` / `NaturalPerson`) ✓ → +0.2
- +1: Nombres claros y consistentes en inglés → +0.1
- **Total: +0.3**

## Nota Final: 3.95 / 5.0

---

## Análisis por Criterio

### 1. Modelado de dominio — 4/5
Entidades presentes: `BankAccount`, `BankProduct` (abstracta), `Client` (abstracta), `CompanyClient`, `NaturalPerson`, `User`, `OperationLog`, `Loan`, `Transfer`. Todas las entidades principales del dominio están implementadas. Diseño destacable: `BankProduct` como clase abstracta que agrupa `BankAccount` y `Loan` refleja correctamente el catálogo bancario unificado.

### 2. Relaciones entre entidades — 4/5
Relaciones bien definidas:
- `BankAccount extends BankProduct` ✓
- `Loan extends BankProduct` ✓
- `Client` tiene `List<BankProduct> products` ✓ (relación directa con productos)
- `Transfer` tiene `BankAccount sourceAccount` y `BankAccount destinationAccount` ✓ (referencias directas, no Strings)
- `Transfer` tiene `List<OperationLog> logs` ✓
- `Loan` tiene `User approvedBy` ✓
- `User` tiene `List<Transfer> transfersCreated` ✓

Punto de mejora: `User` no tiene ningún campo `relatedClient` — la entidad `User` y la jerarquía `Client` son independientes, sin vínculo entre ellas.

### 3. Uso de Enums — 3/5
Enums presentes (5/8):
- `AccountStatus` (ACTIVE, BLOCKED, CANCELLED) ✓
- `LoanStatus` (UNDER_REVIEW, APPROVED, REJECTED, DISBURSED) ✓
- `SystemRole` ✓ (ver nota abajo)
- `TransferStatus` (PENDING_APPROVAL, EXECUTED, REJECTED, EXPIRED) ✓
- `UserStatus` (ACTIVE, INACTIVE, BLOCKED) ✓

Problemas:
- **Falta `AccountType`**: `BankAccount.accountType` es `String` en lugar de enum.
- **Falta `Currency`**: moneda en `BankAccount` no está modelada.
- **Falta `LoanType`**: tipo de préstamo no tiene enum.
- **Falta `OperationType`**: `OperationLog.operationType` es `String` en lugar de enum.
- `SystemRole` mezcla tipos de cliente (`NATURAL_PERSON_CLIENT`, `COMPANY_CLIENT`) con roles de empleado (`TELLER_EMPLOYEE`, `COMMERCIAL_EMPLOYEE`, etc.) — debería ser responsabilidad separada.

### 4. Manejo de estados — 4/5
Los enums de estado están correctamente usados en las entidades:
- `BankAccount` tiene `AccountStatus status` ✓
- `Loan` tiene `LoanStatus status` ✓
- `Transfer` tiene `TransferStatus status` ✓
- `User` tiene `UserStatus status` ✓

Falta implementar métodos que gestionen transiciones de estado (p.ej. `block()`, `cancel()`, `approve()`).

### 5. Tipos de datos — 3/5
- **Fechas:** `LocalDate` en `BankAccount`, `OperationLog`, `Loan`, `Transfer` ✓ — uso correcto.
- **Montos monetarios:** `double` en `BankAccount.currentBalance`, `Loan.requestedAmount`, `Loan.approvedAmount`, `Loan.interestRate`, `Transfer.amount` ❌ — debería ser `BigDecimal` para precisión financiera.

### 6. Separación Usuario vs Cliente — 3/5
La jerarquía `Client → CompanyClient / NaturalPerson` existe y es correcta ✓. `User` es una entidad completamente separada ✓. Sin embargo, `User` no tiene ningún campo que lo vincule con un `Client` (no hay `private Client relatedClient`, ni `String clientId`). El sistema no puede saber qué cliente gestiona cada usuario, lo cual es fundamental para operaciones bancarias.

### 7. Bitácora — 4/5
`OperationLog` bien estructurada:
- `Map<String, Object> detailData` ✓ — datos flexibles de la operación
- `SystemRole userRole` ✓ — rol del usuario usando enum
- `LocalDate timestamp` ✓ — fecha con tipo correcto

Punto de mejora: `operationType` es `String` en lugar de un enum `OperationType` (los tipos de operación deberían estar tipados: DEPOSIT, WITHDRAWAL, TRANSFER, LOAN_DISBURSEMENT, etc.).

### 8. Reglas básicas de negocio — 1/5
Todas las entidades son POJOs con Lombok (`@Getter @Setter`). No hay ningún método de negocio implementado: no existe `deposit()`, `withdraw()`, `approve()`, `block()`, ni validaciones en constructores. La lógica está planificada en el README (los servicios listados) pero no implementada en el dominio.

### 9. Estructura del proyecto — 4/5
Proyecto Maven estructurado correctamente con Spring Boot. Paquetes:
- `app/` — clase principal de arranque
- `domain/models/` — todas las entidades de dominio
- `domain/models/enums/` — enums separados ✓

Diseño de herencia `BankProduct → BankAccount / Loan` es arquitectónicamente correcto. Punto de mejora: todas las entidades están en un único paquete `domain.models` en lugar de sub-paquetes por entidad (`domain.models.account`, `domain.models.client`, etc.).

### 10. Repositorio — 3/5
- **Nombre:** `construcciondesoftware2SantiagoTabares` ✓ — sigue el formato de la materia.
- **README:** Existe ✓ — lista entidades, enums y servicios planificados. Incluye `ClassDiagram.png` como evidencia del diseño.
- **Commits:** 3 commits descriptivos en develop con mensajes en inglés ✓.
- **Ramas:** `main` y `develop` ✓.
- **Tag:** No hay tag de entrega.
- **Observación:** El README tiene título "NOTAS DEL DESARROLLO" en español; no especifica nombre del estudiante, materia ni instrucciones de ejecución.

---

## Fortalezas
- `BankProduct` como clase abstracta unifica `BankAccount` y `Loan` — diseño elegante.
- `Transfer` usa referencias directas a `BankAccount` en lugar de Strings — buenas relaciones de dominio.
- `OperationLog` con `Map<String, Object>` y enum `SystemRole` — bitácora bien estructurada.
- Jerarquía `Client → CompanyClient / NaturalPerson` correcta.
- Todas las entidades del dominio están presentes.
- `ClassDiagram.png` demuestra planificación del diseño.
- Fechas correctamente con `LocalDate`.

## Oportunidades de mejora
- **`User` debe referenciar a `Client`**: agregar `private Client associatedClient` para vincular usuario con el cliente que representa.
- **Agregar enums faltantes**: `AccountType`, `Currency`, `LoanType`, `OperationType`.
- **Usar `BigDecimal`** para `currentBalance`, `requestedAmount`, `approvedAmount`, `amount`.
- **Agregar métodos de negocio**: `deposit()`, `withdraw()` en `BankAccount`; `approve()`, `reject()`, `disburse()` en `Loan`; `execute()`, `expire()` en `Transfer`.
- **Separar `SystemRole`** de tipos de cliente: los valores `NATURAL_PERSON_CLIENT` y `COMPANY_CLIENT` pertenecen al dominio de `Client`, no al rol del sistema del usuario.
- **Usar `@Getter @Setter` consistente**: `NaturalPerson` omite las anotaciones Lombok y usa accesores de estilo record (`public LocalDate birthDate()`) — inconsistente con el resto del proyecto.
- **Agregar sub-paquetes por entidad** en `domain.models`: `account`, `client`, `user`, etc.
- **Agregar tag** de entrega (`v1.0-modelos`).
- **Mejorar README** con nombre del estudiante, materia y cómo ejecutar el proyecto.
