package domain.ports;

import domain.models.Transfer;

public interface CreateInternalTransferPort {

    void execute(Transfer transfer);
}
