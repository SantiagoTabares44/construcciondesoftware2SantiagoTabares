package domain.models;

import java.time.LocalDate;

import domain.models.enums.LoanStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Loan extends BankProduct {
    private int loanId;
    private double requestedAmount;
    private double approvedAmount;
    private double interestRate;
    private int termMonts;
    private LoanStatus status;
    private LocalDate approvalDate;
    private LocalDate disbursementDate;
    private User approvedBy;

    public Loan(String productId,LocalDate creationDate,int loanId,double requestedAmount,double approvedAmount,double interestRate, int termMonts){
        super(productId, creationDate);
    }

    
}
