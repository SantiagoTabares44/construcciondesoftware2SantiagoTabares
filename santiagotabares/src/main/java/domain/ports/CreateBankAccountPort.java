package domain.ports;
import domain.services.commands.*;
public interface CreateBankAccountPort {
    void execute(CreateBankAccountCommand command);
}
