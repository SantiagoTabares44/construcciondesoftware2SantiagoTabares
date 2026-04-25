package domain.ports;

import java.util.List;

import domain.models.Loan;

public interface ConsultClientLoansPort {

    List<Loan> execute(String clientId);
}
