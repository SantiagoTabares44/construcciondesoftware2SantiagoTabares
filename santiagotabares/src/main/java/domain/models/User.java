package domain.models;

import lombok.Getter;
import lombok.Setter;
import java.util.*;

import domain.models.enums.*;
@Getter
@Setter

public class User {
    private int userId;
    private String fullName;
    private String identificationNumber;
    private String email;
    private String phone;
    private String address;
    private SystemRole role;
    private UserStatus status;

    private List<Transfer> transfersCreated;

}
