package domain.models;

import java.util.*;
import domain.Exceptions.BusinessException;
import domain.models.enums.UserStatus;
public abstract class Client {

    protected String name;
    protected String identification;
    protected String email;
    protected String phone;
    protected String address;
    protected UserStatus status;
    protected List<BankProduct> products;


    //Metodo para validar si el cliente esta activo
    public boolean isActive(){
        return status == UserStatus.ACTIVE;
    }

    //Metodo para actualizar la informacion del cliente
    public void updateContactInfo(String email,String phone,String address){

        if(email == null || !email.contains("@")){
            throw new BusinessException("Correo invalido.");
        }
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    //get client
    public String getId(){
        return identification;
    }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public domain.models.enums.UserStatus getStatus() { return status; }
    public String getName() { return name; }
}