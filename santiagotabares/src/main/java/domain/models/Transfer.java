package domain.models;

import java.time.LocalDate;
import java.util.*;

import domain.models.enums.TransferStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Transfer {

    private int transferId;
    private double amount;
    private LocalDate creationDate;
    private BankAccount sourceAccount;
    private BankAccount destinationAccount;
    private List<OperationLog> logs;
    private TransferStatus status;
    

}
