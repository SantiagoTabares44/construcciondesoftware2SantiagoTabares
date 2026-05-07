package application.usecases;

import domain.ports.CreateBankAccountPort;
import domain.services.commands.CreateBankAccountCommand;
import org.springframework.stereotype.Service;

// @Service indica que este es un componente de la capa de aplicación.
// Actúa como punto de entrada para la operación de crear una cuenta.
@Service
public class CreateBankAccountUseCase {

    // Depende del PORT, no del service directamente.
    // Esto permite cambiar la implementación sin tocar este caso de uso.
    private final CreateBankAccountPort createBankAccountPort;

    public CreateBankAccountUseCase(CreateBankAccountPort createBankAccountPort) {
        this.createBankAccountPort = createBankAccountPort;
    }

    public void execute(CreateBankAccountCommand command) {
        createBankAccountPort.execute(command);
    }
}
