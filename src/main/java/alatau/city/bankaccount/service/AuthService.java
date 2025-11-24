package alatau.city.bankaccount.service;

import alatau.city.bankaccount.entities.dto.JwtResponse;
import alatau.city.bankaccount.entities.dto.LoginRequest;
import alatau.city.bankaccount.entities.dto.RegisterRequest;

public interface AuthService {

    void register(RegisterRequest registerRequest);

    JwtResponse login(LoginRequest request);

}
