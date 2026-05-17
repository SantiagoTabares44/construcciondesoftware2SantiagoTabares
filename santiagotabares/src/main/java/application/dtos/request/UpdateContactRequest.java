package application.dtos.request;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateContactRequest {
    private String email;
    private String phone;
    private String address;
}
