package domain.ports;

import java.util.List;

import domain.models.Transfer;

public interface CreateMassPaymentsPort {

    void execute(List<Transfer> transfers);

}
