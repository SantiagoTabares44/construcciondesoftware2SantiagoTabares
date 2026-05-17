package application.controllers;

import application.dtos.request.CreateBankAccountRequest;
import application.dtos.request.DepositWithdrawRequest;
import application.dtos.response.ApiResponse;
import application.dtos.response.BankAccountResponse;
import application.usecases.CreateBankAccountUseCase;
import application.usecases.DepositMoneyUseCase;
import application.usecases.WithdrawMoneyUseCase;
import domain.ports.BlockBankAccountPort;
import domain.ports.ConsultAccountBalancePort;
import domain.ports.ConsultClientAccountsPort;
import domain.services.commands.CreateBankAccountCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounts")
public class BankAccountController {

    private final CreateBankAccountUseCase createBankAccountUseCase;
    private final DepositMoneyUseCase depositMoneyUseCase;
    private final WithdrawMoneyUseCase withdrawMoneyUseCase;
    private final BlockBankAccountPort blockBankAccountPort;
    private final ConsultAccountBalancePort consultBalancePort;
    private final ConsultClientAccountsPort consultClientAccountsPort;

    public BankAccountController(
            CreateBankAccountUseCase createBankAccountUseCase,
            DepositMoneyUseCase depositMoneyUseCase,
            WithdrawMoneyUseCase withdrawMoneyUseCase,
            BlockBankAccountPort blockBankAccountPort,
            ConsultAccountBalancePort consultBalancePort,
            ConsultClientAccountsPort consultClientAccountsPort
    ) {
        this.createBankAccountUseCase = createBankAccountUseCase;
        this.depositMoneyUseCase = depositMoneyUseCase;
        this.withdrawMoneyUseCase = withdrawMoneyUseCase;
        this.blockBankAccountPort = blockBankAccountPort;
        this.consultBalancePort = consultBalancePort;
        this.consultClientAccountsPort = consultClientAccountsPort;
    }

    // POST /api/accounts
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createAccount(
            @RequestBody CreateBankAccountRequest request
    ) {
        CreateBankAccountCommand command = new CreateBankAccountCommand(
                request.getAccountNumber(),
                request.getClientId(),
                request.getBankProductCode(),
                request.getCurrency()
        );
        createBankAccountUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Cuenta bancaria creada exitosamente"));
    }

    // POST /api/accounts/{accountNumber}/deposit
    @PostMapping("/{accountNumber}/deposit")
    public ResponseEntity<ApiResponse<Void>> deposit(
            @PathVariable String accountNumber,
            @RequestBody DepositWithdrawRequest request
    ) {
        depositMoneyUseCase.execute(accountNumber, request.getAmount());
        return ResponseEntity.ok(ApiResponse.ok("Depósito realizado exitosamente"));
    }

    // POST /api/accounts/{accountNumber}/withdraw
    @PostMapping("/{accountNumber}/withdraw")
    public ResponseEntity<ApiResponse<Void>> withdraw(
            @PathVariable String accountNumber,
            @RequestBody DepositWithdrawRequest request
    ) {
        withdrawMoneyUseCase.execute(accountNumber, request.getAmount());
        return ResponseEntity.ok(ApiResponse.ok("Retiro realizado exitosamente"));
    }

    // GET /api/accounts/{accountNumber}/balance
    @GetMapping("/{accountNumber}/balance")
    public ResponseEntity<ApiResponse<BigDecimal>> getBalance(
            @PathVariable String accountNumber
    ) {
        BigDecimal balance = consultBalancePort.execute(accountNumber);
        return ResponseEntity.ok(ApiResponse.ok("Saldo consultado", balance));
    }

    // PATCH /api/accounts/{accountNumber}/block
    @PatchMapping("/{accountNumber}/block")
    public ResponseEntity<ApiResponse<Void>> blockAccount(
            @PathVariable String accountNumber
    ) {
        blockBankAccountPort.execute(accountNumber);
        return ResponseEntity.ok(ApiResponse.ok("Cuenta bloqueada exitosamente"));
    }

    // GET /api/accounts/client/{clientId}
    @GetMapping("/client/{clientId}")
    public ResponseEntity<ApiResponse<List<BankAccountResponse>>> getClientAccounts(
            @PathVariable String clientId
    ) {
        List<BankAccountResponse> accounts = consultClientAccountsPort.execute(clientId)
                .stream()
                .map(BankAccountResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.ok("Cuentas encontradas", accounts));
    }
}
