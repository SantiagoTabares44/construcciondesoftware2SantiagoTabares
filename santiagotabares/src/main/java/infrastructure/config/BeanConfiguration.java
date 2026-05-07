package infrastructure.config;

import domain.ports.*;
import domain.services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration le dice a Spring que esta clase declara Beans.
// Cada método @Bean es una "receta" que Spring usa para construir
// e inyectar objetos automáticamente en toda la aplicación.
@Configuration
public class BeanConfiguration {

    // ─── Beans de Clientes ───────────────────────────────────────────
    // Spring ve que CreateNaturalPersonClientService necesita un
    // ClientRepositoryPort y un RegisterOperationLogPort.
    // Los busca entre los @Component y @Bean disponibles, y los inyecta.

    @Bean
    public CreateNaturalPersonClientPort createNaturalPersonClientPort(
            ClientRepositoryPort clientRepository,
            RegisterOperationLogPort auditPort
    ) {
        return new CreateNaturalPersonClientService(clientRepository, auditPort);
    }

    @Bean
    public CreateCompanyClientPort createCompanyClientPort(
            ClientRepositoryPort clientRepository,
            RegisterOperationLogPort auditPort
    ) {
        return new CreateCompanyClientService(clientRepository, auditPort);
    }

    @Bean
    public UpdateClientContactInformationPort updateClientContactInformationPort(
            ClientRepositoryPort clientRepository,
            RegisterOperationLogPort auditPort
    ) {
        return new UpdateClientContactInformationService(clientRepository, auditPort);
    }

    @Bean
    public ConsultClientInformationPort consultClientInformationPort(
            ClientRepositoryPort clientRepository
    ) {
        return new ConsultClientInformationService(clientRepository);
    }

    // ─── Beans de Cuentas ────────────────────────────────────────────

    @Bean
    public CreateBankAccountPort createBankAccountPort(
            ClientRepositoryPort clientRepository,
            BankAccountRepositoryPort bankAccountRepository,
            BankProductRepositoryPort bankProductRepository,
            RegisterOperationLogPort auditPort
    ) {
        return new CreateBankAccountService(
                clientRepository,
                bankAccountRepository,
                bankProductRepository,
                auditPort
        );
    }

    @Bean
    public DepositMoneyIntoAcountPort depositMoneyPort(
            BankAccountRepositoryPort bankAccountRepository,
            RegisterOperationLogPort auditPort
    ) {
        return new DepositMoneyService(bankAccountRepository, auditPort);
    }

    @Bean
    public WithdrawMoneyFromAccountPort withdrawMoneyPort(
            BankAccountRepositoryPort bankAccountRepository,
            RegisterOperationLogPort auditPort
    ) {
        return new WithdrawMoneyService(bankAccountRepository, auditPort);
    }

    @Bean
    public BlockBankAccountPort blockBankAccountPort(
            BankAccountRepositoryPort bankAccountRepository,
            RegisterOperationLogPort auditPort
    ) {
        return new BlockBankAccountService(bankAccountRepository, auditPort);
    }

    @Bean
    public ConsultAccountBalancePort consultAccountBalancePort(
            BankAccountRepositoryPort bankAccountRepository
    ) {
        return new ConsultAccountBalanceService(bankAccountRepository);
    }

    @Bean
    public ConsultClientAccountsPort consultClientAccountsPort(
            BankAccountRepositoryPort bankAccountRepository,
            ClientRepositoryPort clientRepository
    ) {
        return new ConsultClientAccountsService(bankAccountRepository, clientRepository);
    }

    // ─── Beans de Préstamos ──────────────────────────────────────────

    @Bean
    public CreateLoanRequestPort createLoanRequestPort(
            ClientRepositoryPort clientRepository,
            LoanRepositoryPort loanRepository,
            RegisterOperationLogPort auditPort
    ) {
        return new CreateLoanRequestService(clientRepository, loanRepository, auditPort);
    }

    @Bean
    public ApproveLoanRequestPort approveLoanRequestPort(
            LoanRepositoryPort loanRepository,
            RegisterOperationLogPort auditPort
    ) {
        return new ApproveLoanRequestService(loanRepository, auditPort);
    }

    @Bean
    public RejectLoanRequestPort rejectLoanRequestPort(
            LoanRepositoryPort loanRepository,
            RegisterOperationLogPort auditPort
    ) {
        return new RejectLoanRequestService(loanRepository, auditPort);
    }

    @Bean
    public DisburseLoanPort disburseLoanPort(
            LoanRepositoryPort loanRepository,
            BankAccountRepositoryPort bankAccountRepository,
            RegisterOperationLogPort auditPort
    ) {
        return new DisburseLoanService(loanRepository, bankAccountRepository, auditPort);
    }

    @Bean
    public ConsultClientLoansPort consultClientLoansPort(
            LoanRepositoryPort loanRepository,
            ClientRepositoryPort clientRepository
    ) {
        return new ConsultClientLoansService(loanRepository, clientRepository);
    }

    @Bean
    public ConsultLoanStatusPort consultLoanStatusPort(
            LoanRepositoryPort loanRepository
    ) {
        return new ConsultLoanStatusService(loanRepository);
    }

    // ─── Beans de Transferencias ─────────────────────────────────────

    @Bean
    public CreateInternalTransferPort createInternalTransferPort(
            BankAccountRepositoryPort bankAccountRepository,
            TransferRepositoryPort transferRepository,
            RegisterOperationLogPort auditPort
    ) {
        return new CreateInternalTransferService(bankAccountRepository, transferRepository, auditPort);
    }

    @Bean
    public CreateTransferToThirdPartyPort createTransferToThirdPartyPort(
            BankAccountRepositoryPort bankAccountRepository,
            TransferRepositoryPort transferRepository,
            RegisterOperationLogPort auditPort
    ) {
        return new CreateTransferToThirdPartyService(bankAccountRepository, transferRepository, auditPort);
    }

    @Bean
    public CreateMassPaymentsPort createMassPaymentsPort(
            BankAccountRepositoryPort bankAccountRepository,
            TransferRepositoryPort transferRepository,
            RegisterOperationLogPort auditPort
    ) {
        return new CreateMassPaymentsService(bankAccountRepository, transferRepository, auditPort);
    }

    @Bean
    public ApproveTransferPort approveTransferPort(
            TransferRepositoryPort transferRepository,
            BankAccountRepositoryPort bankAccountRepository,
            RegisterOperationLogPort auditPort
    ) {
        return new ApproveTransferService(transferRepository, bankAccountRepository, auditPort);
    }

    @Bean
    public RejectTransferPort rejectTransferPort(
            TransferRepositoryPort transferRepository,
            RegisterOperationLogPort auditPort
    ) {
        return new RejectTransferService(transferRepository, auditPort);
    }

    @Bean
    public ExpireTransferPort expireTransferPort(
            TransferRepositoryPort transferRepository,
            RegisterOperationLogPort auditPort
    ) {
        return new ExpireTransferService(transferRepository, auditPort);
    }

    @Bean
    public ConsultTransferHistoryPort consultTransferHistoryPort(
            TransferRepositoryPort transferRepository,
            BankAccountRepositoryPort bankAccountRepository
    ) {
        return new ConsultTransferHistoryService(transferRepository, bankAccountRepository);
    }

    // ─── Beans de Usuarios ───────────────────────────────────────────

    @Bean
    public CreateUserForCompanyClientPort createUserForCompanyClientPort(
            UserRepositoryPort userRepository,
            ClientRepositoryPort clientRepository,
            RegisterOperationLogPort auditPort
    ) {
        return new CreateUserForCompanyClientService(userRepository, clientRepository, auditPort);
    }

    @Bean
    public ActivateUserPort activateUserPort(
            UserRepositoryPort userRepository,
            RegisterOperationLogPort auditPort
    ) {
        return new ActivateUserService(userRepository, auditPort);
    }

    @Bean
    public BlockUserPort blockUserPort(
            UserRepositoryPort userRepository,
            RegisterOperationLogPort auditPort
    ) {
        return new BlockUserService(userRepository, auditPort);
    }

    @Bean
    public AssignPermissionsToUserPort assignPermissionsToUserPort(
            UserRepositoryPort userRepository,
            RegisterOperationLogPort auditPort
    ) {
        return new AssignPermissionsToUserService(userRepository, auditPort);
    }

    @Bean
    public ConsultClientUsersPort consultClientUsersPort(
            UserRepositoryPort userRepository,
            ClientRepositoryPort clientRepository
    ) {
        return new ConsultClientUsersService(userRepository, clientRepository);
    }

    // ─── Beans de Auditoría ──────────────────────────────────────────

    @Bean
    public RegisterOperationLogPort registerOperationLogPort(
            OperationLogRepositoryPort operationLogRepository
    ) {
        return new RegisterOperationLogService(operationLogRepository);
    }

    @Bean
    public ConsultAccountOperationsPort consultAccountOperationsPort(
            OperationLogRepositoryPort operationLogRepository,
            BankAccountRepositoryPort bankAccountRepository
    ) {
        return new ConsultAccountOperationsService(operationLogRepository, bankAccountRepository);
    }

    @Bean
    public ConsultOperationLogsPort consultOperationLogsPort(
            OperationLogRepositoryPort operationLogRepository
    ) {
        return new ConsultOperationLogsService(operationLogRepository);
    }
}
