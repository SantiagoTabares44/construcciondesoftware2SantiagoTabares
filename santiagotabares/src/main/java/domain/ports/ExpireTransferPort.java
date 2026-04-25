package domain.ports;

import domain.models.Transfer;

public interface ExpireTransferPort {

    void execute(Transfer transfer);

}
