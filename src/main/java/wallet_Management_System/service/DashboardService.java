package wallet_Management_System.service;


import org.springframework.stereotype.Service;
import wallet_Management_System.dto.response.DashboardResponse;


public interface DashboardService {

    DashboardResponse dashboard(String phoneNumber);
}
