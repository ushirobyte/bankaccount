package alatau.city.bankaccount.service.impl;

import alatau.city.bankaccount.config.security.JwtService;
import alatau.city.bankaccount.entities.Users;
import alatau.city.bankaccount.entities.dto.JwtResponse;
import alatau.city.bankaccount.entities.dto.LoginRequest;
import alatau.city.bankaccount.entities.dto.RegisterRequest;
import alatau.city.bankaccount.repository.UserRepository;
import alatau.city.bankaccount.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public void register(RegisterRequest registerRequest) {

        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new IllegalArgumentException("Username is already taken");
        }

        log.info("Username {} already exists", registerRequest.getUsername());
        log.info("Password {} already exists", registerRequest.getPassword());

        Users users = Users.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role("USER")
                .build();

        userRepository.save(users);
    }

    @Override
    public JwtResponse login(LoginRequest request) {
        Users users = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), users.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        UserDetails userDetails = User
                .withUsername(users.getUsername())
                .password(users.getPassword())
                .roles(users.getRole())
                .build();

        String token = jwtService.generateToken(userDetails);

        return new JwtResponse(token);
    }


}
