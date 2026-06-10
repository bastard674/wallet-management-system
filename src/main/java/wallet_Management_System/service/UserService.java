package wallet_Management_System.service;

import wallet_Management_System.dto.request.RegisterUserRequest;
import wallet_Management_System.dto.response.RegisterUserResponse;

public interface UserService {

    RegisterUserResponse registerUser(RegisterUserRequest request );
}
