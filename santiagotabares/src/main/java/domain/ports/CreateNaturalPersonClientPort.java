package domain.ports;

import domain.models.NaturalPerson;

public interface CreateNaturalPersonClientPort {

    void execute(NaturalPerson person);
    
}
