package wallet_Management_System.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wallet_Management_System.dto.request.RegisterUserRequest;
import wallet_Management_System.dto.response.RegisterUserResponse;
import wallet_Management_System.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public RegisterUserResponse registerUser(
            @RequestBody RegisterUserRequest request) {

        return userService.registerUser(request);
    }


}
