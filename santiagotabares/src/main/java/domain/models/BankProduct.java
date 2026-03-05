package domain.models;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public abstract class BankProduct {

    private String productId;
    private LocalDate creationDate;

    public BankProduct(String productId, LocalDate creationDate) {
        this.productId = productId;
        this.creationDate = creationDate;
    }



}
