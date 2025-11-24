package alatau.city.bankaccount.service.impl;

import alatau.city.bankaccount.entities.User;
import alatau.city.bankaccount.entities.dto.RegisterRequest;
import alatau.city.bankaccount.repository.UserRepository;
import alatau.city.bankaccount.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterRequest registerRequest) {

        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new IllegalArgumentException("Username is already taken");
        }

        log.info("Username {} already exists", registerRequest.getUsername());
        log.info("Password {} already exists", registerRequest.getPassword());

        User user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role("USER")
                .build();

        userRepository.save(user);
    }
}
