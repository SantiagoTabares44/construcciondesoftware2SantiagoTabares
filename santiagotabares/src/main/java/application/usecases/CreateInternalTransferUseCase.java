package application.usecases;

import domain.models.Transfer;
import domain.ports.CreateInternalTransferPort;
import org.springframework.stereotype.Service;

@Service
public class CreateInternalTransferUseCase {

    private final CreateInternalTransferPort createInternalTransferPort;

    public CreateInternalTransferUseCase(CreateInternalTransferPort createInternalTransferPort) {
        this.createInternalTransferPort = createInternalTransferPort;
    }

    public void execute(Transfer transfer) {
        createInternalTransferPort.execute(transfer);
    }
}
