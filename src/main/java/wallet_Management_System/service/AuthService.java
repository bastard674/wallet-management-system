package wallet_Management_System.service;

import wallet_Management_System.dto.request.LoginRequest;
import wallet_Management_System.dto.response.LoginResponse;

public interface AuthService {

    LoginResponse userLogin(LoginRequest request);
}
