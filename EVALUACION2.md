# EVALUACION 2 - construcciondesoftware2SantiagoTabares

## Informacion general
- Estudiante(s): Santiago Tabares (usuario GitHub: SantiagoTabares44)
- Rama evaluada: develop
- Commit evaluado: 9e59b03c4ce245f91c4f7b2d7cb0309734650cd7
- Fecha: 2026-04-11

---

## Tabla de calificacion

| # | Criterio | Peso | Puntaje (1-5) | Parcial |
|---|---|---|---|---|
| 1 | Modelado de dominio | 20% | 4 | 0.80 |
| 2 | Modelado de puertos | 20% | 1 | 0.20 |
| 3 | Modelado de servicios de dominio | 20% | 1 | 0.20 |
| 4 | Enums y estados | 10% | 4 | 0.40 |
| 5 | Reglas de negocio criticas | 10% | 1 | 0.10 |
| 6 | Bitacora y trazabilidad | 5% | 2 | 0.10 |
| 7 | Estructura interna de dominio | 10% | 2 | 0.20 |
| 8 | Calidad tecnica base en domain | 5% | 4 | 0.20 |
| | **Total base** | | | **2.20** |

### Calculo
Nota base = (4*20 + 1*20 + 1*20 + 4*10 + 1*10 + 2*5 + 2*10 + 4*5) / 100 = 220 / 100 = **2.20**

---

## Penalizaciones aplicadas

Ninguna penalizacion mayor aplicable.

---

## Nota final
**2.2 / 5.0**

---

## Hallazgos

### Criterio 1 - Modelado de dominio (4/5)
- Entidades presentes: `Client` (base), `CompanyClient`, `NaturalPerson`, `BankAccount`, `Loan`, `OperationLog`, `Transfer`, `User`, `BankProduct`.
- Buen diseno de jerarquia de clientes con clase base `Client`.
- El README de develop documenta las entidades y enums planeados (buen indicio de diseño intencionado).
- Falta: relacion explicita `BankAccount → Client`.
- Falta: catalogo de productos implementado.

### Criterio 2 - Modelado de puertos (1/5)
- **No existe ningun puerto de dominio.**
- No hay carpeta `domain/ports/` ni interfaces `*Port`.

### Criterio 3 - Servicios de dominio (1/5)
- **No existe ninguna clase de servicio de dominio.**
- El README menciona `# SERVICES` como seccion pero no hay implementacion.

### Criterio 4 - Enums y estados (4/5)
- Enums en `domain/models/enums/`: `AccountStatus`, `LoanStatus`, `SystemRole`, `TransferStatus`, `UserStatus`.
- Subcarpeta `enums/` bien separada.
- Falta: `AccountType`, `Currency`, `LoanType`, `ProductCategory`.

### Criterio 5 - Reglas de negocio criticas (1/5)
- Sin servicios no hay reglas implementadas.

### Criterio 6 - Bitacora y trazabilidad (2/5)
- `OperationLog` existe como entidad de dominio.
- Sin puerto ni servicio de bitacora.

### Criterio 7 - Estructura interna de dominio (2/5)
- Existe `domain/models/` y `domain/models/enums/` — buena base.
- Falta por completo: `domain/ports/` y `domain/services/`.

### Criterio 8 - Calidad tecnica (4/5)
- Nomenclatura en ingles consistente.
- Estructura clara y nombres coherentes.
- Sin typos detectados.

---

## Recomendaciones
1. Implementar los servicios planeados en el README: completar `domain/services/` con los casos de uso.
2. Crear `domain/ports/` con las interfaces de contrato de salida del dominio.
3. Agregar los enums faltantes: `AccountType`, `Currency`, `LoanType`.
4. Implementar reglas criticas en los servicios: ciclo de prestamo, validacion de saldo, vencimiento de transferencias.
