package infrastructure.config;

import domain.ports.*;
import infrastructure.persistence.adapters.BankProductRepositoryAdapter;
import domain.services.*;
import infrastructure.persistence.adapters.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration le dice a Spring que esta clase declara beans
// Cada @Bean es un "caso de uso" que Spring crea y gestiona
@Configuration
public class UseCasesConfig {

    // ─────────────────────────────────────────────────────────
    // ADAPTADORES (repositorios concretos)
    // Spring los inyecta donde se necesite el port correspondiente
    // ─────────────────────────────────────────────────────────

    @Bean
    public ClientRepositoryPort clientRepositoryPort(ClientRepositoryAdapter adapter) {
        return adapter;
    }

    @Bean
    public BankAccountRepositoryPort bankAccountRepositoryPort(BankAccountRepositoryAdapter adapter) {
        return adapter;
    }

    @Bean
    public LoanRepositoryPort loanRepositoryPort(LoanRepositoryAdapter adapter) {
        return adapter;
    }

    @Bean
    public TransferRepositoryPort transferRepositoryPort(TransferRepositoryAdapter adapter) {
        return adapter;
    }

    @Bean
    public UserRepositoryPort userRepositoryPort(UserRepositoryAdapter adapter) {
        return adapter;
    }

    @Bean
    public OperationLogRepositoryPort operationLogRepositoryPort(OperationLogRepositoryAdapter adapter) {
        return adapter;
    }

    @Bean
    public BankProductRepositoryPort bankProductRepositoryPort(BankProductRepositoryAdapter adapter) {
        return adapter;
    }

    // ─────────────────────────────────────────────────────────
    // CASO DE USO: Auditoría
    // RegisterOperationLogService necesita el repositorio de logs.
    // Se declara primero porque otros servicios dependen de él.
    // ─────────────────────────────────────────────────────────

    @Bean
    public RegisterOperationLogPort registerOperationLogPort(OperationLogRepositoryPort repo) {
        return new RegisterOperationLogService(repo);
    }

    // ─────────────────────────────────────────────────────────
    // CASOS DE USO: Clientes
    // ─────────────────────────────────────────────────────────

    @Bean
    public CreateNaturalPersonClientPort createNaturalPersonClientPort(
            ClientRepositoryPort clientRepo,
            RegisterOperationLogPort audit
    ) {
        return new CreateNaturalPersonClientService(clientRepo, audit);
    }

    @Bean
    public CreateCompanyClientPort createCompanyClientPort(
            ClientRepositoryPort clientRepo,
            RegisterOperationLogPort audit
    ) {
        return new CreateCompanyClientService(clientRepo, audit);
    }

    @Bean
    public UpdateClientContactInformationPort updateClientContactInformationPort(
            ClientRepositoryPort clientRepo,
            RegisterOperationLogPort audit
    ) {
        return new UpdateClientContactInformationService(clientRepo, audit);
    }

    @Bean
    public ConsultClientInformationPort consultClientInformationPort(ClientRepositoryPort clientRepo) {
        return new ConsultClientInformationService(clientRepo);
    }

    // ─────────────────────────────────────────────────────────
    // CASOS DE USO: Cuentas bancarias
    // ─────────────────────────────────────────────────────────

    @Bean
    public CreateBankAccountPort createBankAccountPort(
            ClientRepositoryPort clientRepo,
            BankAccountRepositoryPort accountRepo,
            BankProductRepositoryPort bankProductRepo,
            RegisterOperationLogPort audit
    ) {
        return new CreateBankAccountService(clientRepo, accountRepo, bankProductRepo, audit);
    }

    @Bean
    public DepositMoneyIntoAcountPort depositMoneyIntoAcountPort(
            BankAccountRepositoryPort accountRepo,
            RegisterOperationLogPort audit
    ) {
        return new DepositMoneyService(accountRepo, audit);
    }

    @Bean
    public WithdrawMoneyFromAccountPort withdrawMoneyFromAccountPort(
            BankAccountRepositoryPort accountRepo,
            RegisterOperationLogPort audit
    ) {
        return new WithdrawMoneyService(accountRepo, audit);
    }

    @Bean
    public BlockBankAccountPort blockBankAccountPort(
            BankAccountRepositoryPort accountRepo,
            RegisterOperationLogPort audit
    ) {
        return new BlockBankAccountService(accountRepo, audit);
    }

    @Bean
    public ConsultAccountBalancePort consultAccountBalancePort(BankAccountRepositoryPort accountRepo) {
        return new ConsultAccountBalanceService(accountRepo);
    }

    @Bean
    public ConsultClientAccountsPort consultClientAccountsPort(
            BankAccountRepositoryPort accountRepo,
            ClientRepositoryPort clientRepo
    ) {
        return new ConsultClientAccountsService(accountRepo, clientRepo);
    }

    @Bean
    public ConsultAccountOperationsPort consultAccountOperationsPort(
            OperationLogRepositoryPort logRepo,
            BankAccountRepositoryPort accountRepo
    ) {
        return new ConsultAccountOperationsService(logRepo, accountRepo);
    }

    // ─────────────────────────────────────────────────────────
    // CASOS DE USO: Préstamos
    // ─────────────────────────────────────────────────────────

    @Bean
    public CreateLoanRequestPort createLoanRequestPort(
            ClientRepositoryPort clientRepo,
            LoanRepositoryPort loanRepo,
            RegisterOperationLogPort audit
    ) {
        return new CreateLoanRequestService(clientRepo, loanRepo, audit);
    }

    @Bean
    public ApproveLoanRequestPort approveLoanRequestPort(
            LoanRepositoryPort loanRepo,
            RegisterOperationLogPort audit
    ) {
        return new ApproveLoanRequestService(loanRepo, audit);
    }

    @Bean
    public RejectLoanRequestPort rejectLoanRequestPort(
            LoanRepositoryPort loanRepo,
            RegisterOperationLogPort audit
    ) {
        return new RejectLoanRequestService(loanRepo, audit);
    }

    @Bean
    public DisburseLoanPort disburseLoanPort(
            LoanRepositoryPort loanRepo,
            BankAccountRepositoryPort accountRepo,
            RegisterOperationLogPort audit
    ) {
        return new DisburseLoanService(loanRepo, accountRepo, audit);
    }

    @Bean
    public ConsultClientLoansPort consultClientLoansPort(
            LoanRepositoryPort loanRepo,
            ClientRepositoryPort clientRepo
    ) {
        return new ConsultClientLoansService(loanRepo, clientRepo);
    }

    @Bean
    public ConsultLoanStatusPort consultLoanStatusPort(LoanRepositoryPort loanRepo) {
        return new ConsultLoanStatusService(loanRepo);
    }

    // ─────────────────────────────────────────────────────────
    // CASOS DE USO: Transferencias
    // ─────────────────────────────────────────────────────────

    @Bean
    public CreateTransferPort createTransferPort(
            BankAccountRepositoryPort accountRepo,
            TransferRepositoryPort transferRepo,
            RegisterOperationLogPort audit
    ) {
        return new CreateTransferService(accountRepo, transferRepo, audit);
    }

    @Bean
    public CreateInternalTransferPort createInternalTransferPort(
            BankAccountRepositoryPort accountRepo,
            TransferRepositoryPort transferRepo,
            RegisterOperationLogPort audit
    ) {
        return new CreateInternalTransferService(accountRepo, transferRepo, audit);
    }

    @Bean
    public CreateTransferToThirdPartyPort createTransferToThirdPartyPort(
            BankAccountRepositoryPort accountRepo,
            TransferRepositoryPort transferRepo,
            RegisterOperationLogPort audit
    ) {
        return new CreateTransferToThirdPartyService(accountRepo, transferRepo, audit);
    }

    @Bean
    public ApproveTransferPort approveTransferPort(
            TransferRepositoryPort transferRepo,
            BankAccountRepositoryPort accountRepo,
            RegisterOperationLogPort audit
    ) {
        return new ApproveTransferService(transferRepo, accountRepo, audit);
    }

    @Bean
    public RejectTransferPort rejectTransferPort(
            TransferRepositoryPort transferRepo,
            RegisterOperationLogPort audit
    ) {
        return new RejectTransferService(transferRepo, audit);
    }

    @Bean
    public ExpireTransferPort expireTransferPort(
            TransferRepositoryPort transferRepo,
            RegisterOperationLogPort audit
    ) {
        return new ExpireTransferService(transferRepo, audit);
    }

    @Bean
    public CreateMassPaymentsPort createMassPaymentsPort(
            BankAccountRepositoryPort accountRepo,
            TransferRepositoryPort transferRepo,
            RegisterOperationLogPort audit
    ) {
        return new CreateMassPaymentsService(accountRepo, transferRepo, audit);
    }

    @Bean
    public ConsultTransferHistoryPort consultTransferHistoryPort(
            TransferRepositoryPort transferRepo,
            BankAccountRepositoryPort accountRepo
    ) {
        return new ConsultTransferHistoryService(transferRepo, accountRepo);
    }

    // ─────────────────────────────────────────────────────────
    // CASOS DE USO: Usuarios
    // ─────────────────────────────────────────────────────────

    @Bean
    public CreateUserForCompanyClientPort createUserForCompanyClientPort(
            UserRepositoryPort userRepo,
            ClientRepositoryPort clientRepo,
            RegisterOperationLogPort audit
    ) {
        return new CreateUserForCompanyClientService(userRepo, clientRepo, audit);
    }

    @Bean
    public ActivateUserPort activateUserPort(
            UserRepositoryPort userRepo,
            RegisterOperationLogPort audit
    ) {
        return new ActivateUserService(userRepo, audit);
    }

    @Bean
    public BlockUserPort blockUserPort(
            UserRepositoryPort userRepo,
            RegisterOperationLogPort audit
    ) {
        return new BlockUserService(userRepo, audit);
    }

    @Bean
    public AssignPermissionsToUserPort assignPermissionsToUserPort(
            UserRepositoryPort userRepo,
            RegisterOperationLogPort audit
    ) {
        return new AssignPermissionsToUserService(userRepo, audit);
    }

    @Bean
    public ConsultClientUsersPort consultClientUsersPort(
            UserRepositoryPort userRepo,
            ClientRepositoryPort clientRepo
    ) {
        return new ConsultClientUsersService(userRepo, clientRepo);
    }

    @Bean
    public ConsultOperationLogsPort consultOperationLogsPort(OperationLogRepositoryPort logRepo) {
        return new ConsultOperationLogsService(logRepo);
    }
}
