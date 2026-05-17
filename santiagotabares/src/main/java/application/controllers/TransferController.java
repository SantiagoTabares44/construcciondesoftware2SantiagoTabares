package application.controllers;

import application.dtos.request.CreateTransferRequest;
import application.dtos.response.ApiResponse;
import application.dtos.response.TransferResponse;
import application.usecases.CreateInternalTransferUseCase;
import domain.models.BankAccount;
import domain.models.Transfer;
import domain.ports.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final CreateInternalTransferUseCase createInternalTransferUseCase;
    private final CreateTransferToThirdPartyPort createTransferToThirdPartyPort;
    private final ApproveTransferPort approveTransferPort;
    private final RejectTransferPort rejectTransferPort;
    private final ConsultTransferHistoryPort consultTransferHistoryPort;

    public TransferController(
            CreateInternalTransferUseCase createInternalTransferUseCase,
            CreateTransferToThirdPartyPort createTransferToThirdPartyPort,
            ApproveTransferPort approveTransferPort,
            RejectTransferPort rejectTransferPort,
            ConsultTransferHistoryPort consultTransferHistoryPort
    ) {
        this.createInternalTransferUseCase = createInternalTransferUseCase;
        this.createTransferToThirdPartyPort = createTransferToThirdPartyPort;
        this.approveTransferPort = approveTransferPort;
        this.rejectTransferPort = rejectTransferPort;
        this.consultTransferHistoryPort = consultTransferHistoryPort;
    }

    // POST /api/transfers/internal
    @PostMapping("/internal")
    public ResponseEntity<ApiResponse<Void>> createInternalTransfer(
            @RequestBody CreateTransferRequest request
    ) {
        Transfer transfer = buildTransfer(request);
        createInternalTransferUseCase.execute(transfer);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Transferencia interna realizada exitosamente"));
    }

    // POST /api/transfers/third-party
    @PostMapping("/third-party")
    public ResponseEntity<ApiResponse<Void>> createThirdPartyTransfer(
            @RequestBody CreateTransferRequest request
    ) {
        Transfer transfer = buildTransfer(request);
        createTransferToThirdPartyPort.execute(transfer);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Transferencia a tercero creada, pendiente de aprobación"));
    }

    // PATCH /api/transfers/{transferId}/approve
    @PatchMapping("/{transferId}/approve")
    public ResponseEntity<ApiResponse<Void>> approveTransfer(@PathVariable String transferId) {
        approveTransferPort.execute(transferId);
        return ResponseEntity.ok(ApiResponse.ok("Transferencia aprobada y ejecutada"));
    }

    // PATCH /api/transfers/{transferId}/reject
    @PatchMapping("/{transferId}/reject")
    public ResponseEntity<ApiResponse<Void>> rejectTransfer(@PathVariable String transferId) {
        rejectTransferPort.execute(transferId);
        return ResponseEntity.ok(ApiResponse.ok("Transferencia rechazada"));
    }

    // GET /api/transfers/account/{accountNumber}
    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<ApiResponse<List<TransferResponse>>> getTransferHistory(
            @PathVariable String accountNumber
    ) {
        List<TransferResponse> transfers = consultTransferHistoryPort.execute(accountNumber)
                .stream()
                .map(TransferResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.ok("Historial de transferencias", transfers));
    }

    // Helper: construye un Transfer temporal con cuentas stub para pasar al service
    private Transfer buildTransfer(CreateTransferRequest request) {
        BankAccount origin = BankAccount.stub(request.getOriginAccountNumber());
        BankAccount destination = BankAccount.stub(request.getDestinationAccountNumber());
        return Transfer.create(origin, destination, request.getAmount());
    }
}
