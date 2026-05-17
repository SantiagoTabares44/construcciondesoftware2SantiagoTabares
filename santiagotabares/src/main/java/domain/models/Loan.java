package domain.models;

import domain.Exceptions.BusinessException;
import domain.models.enums.LoanStatus;

import java.math.BigDecimal;

public class Loan {

    private String id;
    private Client client;
    private BigDecimal approvedAmount;
    private LoanStatus status;
    private String clientIdRef; // usado cuando se crea desde el controller


    // Sobrecarga para crear préstamo cuando solo se tiene el clientId (desde el controller)
    public static Loan create(String id, String clientId) {
        Loan loan = new Loan();
        loan.id = id;
        // Client stub — el service carga el cliente completo desde la BD
        loan.client = null;
        loan.clientIdRef = clientId;
        loan.status = LoanStatus.UNDER_REVIEW;
        return loan;
    }

    public static Loan create(String id, Client client) {
        Loan loan = new Loan();
        loan.id = id;
        loan.client = client;
        loan.status = LoanStatus.UNDER_REVIEW;
        return loan;
    }

    public void approve(BigDecimal amount) {
        if (status != LoanStatus.UNDER_REVIEW) {
            throw new BusinessException("Loan cannot be approved in current state");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Approved amount must be greater than zero");
        }
        this.approvedAmount = amount;
        this.status = LoanStatus.APPROVED;
    }

    public void reject() {
        status = LoanStatus.REJECTED;
    }

    public void disburse() {
        if (status != LoanStatus.APPROVED) {
            throw new BusinessException("Only approved loans can be disbursed");
        }
        status = LoanStatus.DISBURSED;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public BigDecimal getApprovedAmount() {
        return approvedAmount;
    }

    public String getId() {
        return id;
    }

    public String getClientId() {
        if (client != null) return client.getId();
        return clientIdRef;
    }
    // Usado SOLO por infraestructura para reconstruir desde base de datos
    public static Loan reconstitute(String id, Client client, java.math.BigDecimal approvedAmount, domain.models.enums.LoanStatus status) {
        Loan loan = new Loan();
        loan.id = id;
        loan.client = client;
        loan.approvedAmount = approvedAmount;
        loan.status = status;
        return loan;
    }

    public Client getClient() { return client; }
}