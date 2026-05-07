package application.usecases;

import domain.models.NaturalPerson;
import domain.ports.CreateNaturalPersonClientPort;
import org.springframework.stereotype.Service;

@Service
public class CreateNaturalPersonUseCase {

    private final CreateNaturalPersonClientPort createClientPort;

    public CreateNaturalPersonUseCase(CreateNaturalPersonClientPort createClientPort) {
        this.createClientPort = createClientPort;
    }

    public void execute(NaturalPerson person) {
        createClientPort.execute(person);
    }
}
