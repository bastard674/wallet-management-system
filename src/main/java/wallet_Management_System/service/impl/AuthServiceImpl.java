package wallet_Management_System.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wallet_Management_System.Entity.User;
import wallet_Management_System.dto.request.LoginRequest;
import wallet_Management_System.dto.response.LoginResponse;
import wallet_Management_System.repository.UserRepository;
import wallet_Management_System.service.AuthService;
import wallet_Management_System.util.JwtUtil;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl  implements AuthService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;



    @Override
    public LoginResponse userLogin(LoginRequest request){

        if(userRepository.findByPhoneNumber(request.getPhoneNumber()).isEmpty()){
            throw new RuntimeException("phone number not exists !!");
        };
        User user = userRepository.findByPhoneNumber(request.getPhoneNumber()).get();

        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new RuntimeException("Invalid Credentials");
        };

        String token = jwtUtil.generateToken(user.getPhoneNumber());
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setMessage("LOGIN SUCCESSFULL");
        return response;

    }


}
