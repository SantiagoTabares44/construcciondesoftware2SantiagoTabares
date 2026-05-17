package application.controllers;

import application.dtos.request.ApproveLoanRequest;
import application.dtos.request.CreateLoanRequest;
import application.dtos.response.ApiResponse;
import application.dtos.response.LoanResponse;
import application.usecases.ApproveLoanUseCase;
import application.usecases.CreateLoanRequestUseCase;
import domain.models.Loan;
import domain.ports.ConsultClientLoansPort;
import domain.ports.ConsultLoanStatusPort;
import domain.ports.DisburseLoanPort;
import domain.ports.RejectLoanRequestPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final CreateLoanRequestUseCase createLoanRequestUseCase;
    private final ApproveLoanUseCase approveLoanUseCase;
    private final RejectLoanRequestPort rejectLoanPort;
    private final DisburseLoanPort disburseLoanPort;
    private final ConsultClientLoansPort consultClientLoansPort;
    private final ConsultLoanStatusPort consultLoanStatusPort;

    public LoanController(
            CreateLoanRequestUseCase createLoanRequestUseCase,
            ApproveLoanUseCase approveLoanUseCase,
            RejectLoanRequestPort rejectLoanPort,
            DisburseLoanPort disburseLoanPort,
            ConsultClientLoansPort consultClientLoansPort,
            ConsultLoanStatusPort consultLoanStatusPort
    ) {
        this.createLoanRequestUseCase = createLoanRequestUseCase;
        this.approveLoanUseCase = approveLoanUseCase;
        this.rejectLoanPort = rejectLoanPort;
        this.disburseLoanPort = disburseLoanPort;
        this.consultClientLoansPort = consultClientLoansPort;
        this.consultLoanStatusPort = consultLoanStatusPort;
    }

    // POST /api/loans
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createLoan(
            @RequestBody CreateLoanRequest request
    ) {
        Loan loan = Loan.create(request.getLoanId(), request.getClientId());
        createLoanRequestUseCase.execute(loan);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Solicitud de préstamo creada exitosamente"));
    }

    // PATCH /api/loans/{loanId}/approve
    @PatchMapping("/{loanId}/approve")
    public ResponseEntity<ApiResponse<Void>> approveLoan(
            @PathVariable String loanId,
            @RequestBody ApproveLoanRequest request
    ) {
        approveLoanUseCase.execute(loanId, request.getApprovedAmount());
        return ResponseEntity.ok(ApiResponse.ok("Préstamo aprobado exitosamente"));
    }

    // PATCH /api/loans/{loanId}/reject
    @PatchMapping("/{loanId}/reject")
    public ResponseEntity<ApiResponse<Void>> rejectLoan(@PathVariable String loanId) {
        rejectLoanPort.execute(loanId);
        return ResponseEntity.ok(ApiResponse.ok("Préstamo rechazado"));
    }

    // PATCH /api/loans/{loanId}/disburse
    @PatchMapping("/{loanId}/disburse")
    public ResponseEntity<ApiResponse<Void>> disburseLoan(
            @PathVariable String loanId,
            @RequestParam String accountNumber
    ) {
        disburseLoanPort.execute(loanId, accountNumber);
        return ResponseEntity.ok(ApiResponse.ok("Préstamo desembolsado exitosamente"));
    }

    // GET /api/loans/client/{clientId}
    @GetMapping("/client/{clientId}")
    public ResponseEntity<ApiResponse<List<LoanResponse>>> getClientLoans(
            @PathVariable String clientId
    ) {
        List<LoanResponse> loans = consultClientLoansPort.execute(clientId)
                .stream()
                .map(LoanResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.ok("Préstamos encontrados", loans));
    }

    // GET /api/loans/{loanId}/status
    @GetMapping("/{loanId}/status")
    public ResponseEntity<ApiResponse<String>> getLoanStatus(@PathVariable String loanId) {
        String status = consultLoanStatusPort.execute(loanId).name();
        return ResponseEntity.ok(ApiResponse.ok("Estado del préstamo", status));
    }
}
