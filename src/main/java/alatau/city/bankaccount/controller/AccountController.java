package alatau.city.bankaccount.controller;

import alatau.city.bankaccount.entities.dto.AccountDTO;
import alatau.city.bankaccount.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
@Tag(name = "Accounts", description = "Account operations and money transfers")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/transfer")
    @Operation(summary = "Transfer money between accounts",
                description = "Transfers specified amount from sender account to receiver account"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transfer completed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request (validation or business error"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> transfer(@Valid @RequestBody AccountDTO accountDTO) {
        accountService.transfer(accountDTO);
        return ResponseEntity.ok("transfer completed successfully");
    }

    @GetMapping("/{accountNumber}/transactions")
    @Operation(summary = "Get transaction history",
            description = "Returns list of transactions where this account is sender or receiver")
    public ResponseEntity<?> getTransactionResponseList(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.getTransactionResponseList(accountNumber));
    }

}
