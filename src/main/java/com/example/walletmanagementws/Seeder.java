package com.example.walletmanagementws;

import com.example.walletmanagementws.config.BasicPasswordEncoder;
import com.example.walletmanagementws.entity.User;
import com.example.walletmanagementws.entity.Wallet;
import com.example.walletmanagementws.repository.UserRepository;
import com.example.walletmanagementws.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@RequiredArgsConstructor
public class Seeder {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            saveUser();
        };
    }

    public void saveUser() {
        byte[] saltForUser1 = BasicPasswordEncoder.generateSalt();
        Base64.Encoder encoder = Base64.getEncoder();
        String saltString = new String(encoder.encode(saltForUser1));
        String encodedPasswordForUser1 = BasicPasswordEncoder.encode("1234", saltForUser1);
        User user = new User("johnbro1", "johnbro1@mail.com", encodedPasswordForUser1, saltString, 10_000_000L);
        User savedUser = userRepository.save(user);
        Wallet wallet = new Wallet();
        wallet.setWalletBalance(0L);
        wallet.setUserId(savedUser.getId());
        wallet.setWalletName("Virtual walllet");
        walletRepository.save(wallet);
    }
}
