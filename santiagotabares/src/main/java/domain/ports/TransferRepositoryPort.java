package domain.ports;

import domain.models.Transfer;
import java.util.Optional;
import java.util.List;

public interface TransferRepositoryPort {
    Optional<Transfer> findById(String transferId);
    void save(Transfer transfer);
    List<Transfer> findByAccountNumber(String accountNumber);
}
