package domain.ports;

import domain.models.Transfer;

public interface CreateTransferToThirdPartyPort {

    void execute(Transfer transfer);
}


