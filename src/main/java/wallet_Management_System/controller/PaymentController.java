package wallet_Management_System.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wallet_Management_System.dto.request.TransferRequest;
import wallet_Management_System.dto.response.TransferResponse;
import wallet_Management_System.service.PaymentService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class PaymentController {

        private final PaymentService paymentService;

        @PostMapping("/transfer")
        public TransferResponse sendMoney(@RequestBody TransferRequest request){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return paymentService.sendMoney(request,authentication.getName());

        }
}
