package wallet_Management_System.service;

import wallet_Management_System.dto.request.TransferRequest;
import wallet_Management_System.dto.response.TransferResponse;

public interface PaymentService {
    TransferResponse sendMoney(TransferRequest request,String phoneNUmber);
}
