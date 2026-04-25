package domain.ports;

import domain.models.Transfer;
import java.util.List;

public interface ConsultTransferHistoryPort {

    List<Transfer> execute(String accountNumber);
}
