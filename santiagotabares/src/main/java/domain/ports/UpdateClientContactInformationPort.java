package domain.ports;

public interface UpdateClientContactInformationPort {

    void execute(String clientId, String email, String phone, String address);
}
