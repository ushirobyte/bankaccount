package alatau.city.bankaccount.controller;

import alatau.city.bankaccount.entities.dto.AccountDTO;
import alatau.city.bankaccount.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@Valid @RequestBody AccountDTO accountDTO) {
        accountService.transfer(accountDTO);
        return ResponseEntity.ok("transfer completed successfully");
    }

    @GetMapping("/{accountNumber}/transactions")
    public ResponseEntity<?> getTransactionResponseList(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.getTransactionResponseList(accountNumber));
    }

}
