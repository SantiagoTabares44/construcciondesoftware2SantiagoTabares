package domain.ports;

import domain.models.Client;

public interface ConsultClientInformationPort {

    Client execute(String clientId);

}
