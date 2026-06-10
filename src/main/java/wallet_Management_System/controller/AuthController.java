package wallet_Management_System.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wallet_Management_System.dto.request.LoginRequest;
import wallet_Management_System.dto.response.LoginResponse;
import wallet_Management_System.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse userLogin(@RequestBody LoginRequest request) {
        return authService.userLogin(request);

    }
}
