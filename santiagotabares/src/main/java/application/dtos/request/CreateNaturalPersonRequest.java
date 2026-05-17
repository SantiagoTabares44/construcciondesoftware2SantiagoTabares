package application.dtos.request;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter @Setter
public class CreateNaturalPersonRequest {
    private String identification;
    private String fullName;
    private LocalDate birthDate;
    private String email;
    private String phone;
    private String address;
}
