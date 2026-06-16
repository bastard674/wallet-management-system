package wallet_Management_System.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import wallet_Management_System.config.SecurityConfig;
import wallet_Management_System.dto.request.DepositRequest;
import wallet_Management_System.dto.request.RegisterUserRequest;
import wallet_Management_System.dto.request.WithdrawRequest;
import wallet_Management_System.dto.response.DashboardResponse;
import wallet_Management_System.dto.response.DepositResponse;
import wallet_Management_System.dto.response.RegisterUserResponse;
import wallet_Management_System.dto.response.WithdrawResponse;
import wallet_Management_System.service.DashboardService;
import wallet_Management_System.service.DepositService;
import wallet_Management_System.service.UserService;
import wallet_Management_System.service.WithdrawService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final DashboardService dashboardService;
    private final DepositService depositService;
    private final WithdrawService withdrawService;

    @PostMapping("/register")
    public RegisterUserResponse registerUser(
            @RequestBody RegisterUserRequest request) {

        return userService.registerUser(request);
    }

    @GetMapping("/dashboard")
    public DashboardResponse dashboard(){
        System.out.println("inside the dashboard controller");
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        return dashboardService.dashboard(authentication.getName());
    }

    @PostMapping("/deposit")
    public DepositResponse doDeposit(@RequestBody DepositRequest depositRequest){
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        return depositService.deposit(authentication.getName(), depositRequest.getAmount());
    }

    @PostMapping("/withdraw")
    public WithdrawResponse doWithdraw(@RequestBody WithdrawRequest withdrawRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return withdrawService.withdraw(authentication.getName(), withdrawRequest.getAmount());
    }
}
