package wallet_Management_System.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wallet_Management_System.Entity.User;
import wallet_Management_System.Entity.Wallet;
import wallet_Management_System.dto.request.RegisterUserRequest;
import wallet_Management_System.dto.response.RegisterUserResponse;
import wallet_Management_System.repository.UserRepository;
import wallet_Management_System.repository.WalletRepository;
import wallet_Management_System.service.UserService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WalletRepository walletRepository;

    private final PasswordEncoder passwordEncoder;



    @Transactional
    @Override
    public RegisterUserResponse registerUser(RegisterUserRequest request) {
        if (userRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
           throw new RuntimeException("phone number already exists");
        }

        //create User
        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setPhoneNumber(request.getPhoneNumber());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());

        //save User
        User savedUser = userRepository.save(newUser);

        //wallet purpose
        Wallet wallet = new Wallet();
        wallet.setUser(savedUser);
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setCreatedAt(LocalDateTime.now());
        wallet.setUpdatedAt(LocalDateTime.now());

        walletRepository.save(wallet);

        RegisterUserResponse response = new RegisterUserResponse();
        response.setName(savedUser.getName());
        response.setUserId(savedUser.getUserId());
        response.setPhoneNUmber(savedUser.getPhoneNumber());
        response.setMessage("REGISTRATION SUCCESS");

        return response;



    };
}
