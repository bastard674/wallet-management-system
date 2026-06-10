package wallet_Management_System.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserRequest {

    private String name ;

    private String phoneNumber;

    private String password;

}
