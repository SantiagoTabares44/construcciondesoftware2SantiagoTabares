package domain.ports;

import domain.models.Transfer;

public interface CreateTransferPort {

    void execute(Transfer transfer);
}
