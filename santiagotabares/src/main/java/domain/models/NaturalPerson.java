package domain.models;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.cglib.core.Local;

import domain.Exceptions.BusinessException;
import domain.models.enums.*;

public class NaturalPerson extends Client {
    private LocalDate birthDate;
    private String fullName;


    //get birthdate
    public LocalDate birthDate() {
        return birthDate;
    }
    
    //set birthdate
    public void birthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    //metodo para crear una persona natural

    public static NaturalPerson create(String id,String fullName,LocalDate birthDate)
    {
        if (Period.between(birthDate, LocalDate.now()).getYears() < 18) {
            throw new BusinessException("La edad minima para un cliente son 18 años.");
        }

        NaturalPerson person = new NaturalPerson();
        person.identification = id;
        person.name = fullName;
        person.birthDate = birthDate;
        person.status = UserStatus.ACTIVE;

        return person;
    }
}
