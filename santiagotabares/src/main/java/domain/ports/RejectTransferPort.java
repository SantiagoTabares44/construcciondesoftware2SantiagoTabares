package domain.ports;

public interface RejectTransferPort {

    void execute(String transferId);
}
