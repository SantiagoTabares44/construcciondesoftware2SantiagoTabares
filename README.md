# NOTAS DEL DESARROLLO
# ENTIDADES
-User
-Transfer
-OperationLog
-NaturalPerson
-Loan
-CompanyClient
-Client
-BankProduct
-BankAccount

# ENUMS
-AccountStatus
-LoanStatus
-SystemRole
-TransferStatus
-UserStatus

# SERVICES

# --> Accounts

-createBankAccount

-consultAccountBalance

-consultClientAccounts

-depositMoneyIntoAccount

-withdrawMoneyFromAccount

-blockBankAccount

-consultAccountOperations


# --> Transfers

-CreateTransfer

-createTransferToThirdParty

-createInternalTransfer

-createMassPayments

-consultTransferHistory

-approveTransfer

-rejectTransfer

-expireTransfer

# --> Loans

-createLoanRequest

-consultClientLoans

-approveLoanRequest

-rejectLoanRequest

-disburseLoan

-consultLoanStatus

# --> Clients

-createNaturalPersonClient

-createCompanyClient

-consultClientInformation

-updateClientContactInformation

# --> Users

-createUserForCompanyClient

-assignPermissionsToUser

-consultClientUsers

-activateUser

-blockUser

# --> Audit

-registerOperationLog

-consultOperationLogs

-consultAuditInformation