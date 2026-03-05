package domain.models;

import java.time.LocalDate;

public class NaturalPerson extends Client {
    private LocalDate birthDate;

    public NaturalPerson(String name,String identification, String email, String phone, String address,LocalDate birthDate) {
        super(name,identification, email, phone, address);
        this.birthDate = birthDate;

    }

    public LocalDate birthDate() {
        return birthDate;
    }
    
    public void birthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

}
