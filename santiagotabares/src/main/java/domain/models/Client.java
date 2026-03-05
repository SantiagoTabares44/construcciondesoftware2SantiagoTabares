package domain.models;
import java.util.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Client {

    private String name;
    private String identification;
    private String email;
    private String phone;
    private String address;
    private List<BankProduct> products;


    //Constructor
    public Client(String name,String identification,String email,String phone,String address){
        this.name = name;
        this.identification = identification;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

}
