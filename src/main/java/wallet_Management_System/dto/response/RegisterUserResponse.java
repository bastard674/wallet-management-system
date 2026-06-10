package wallet_Management_System.dto.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserResponse {

    private Long userId;
    private String name;
    private String phoneNUmber;
    private String message;
}
