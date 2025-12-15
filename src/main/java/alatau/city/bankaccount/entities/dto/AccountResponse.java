package alatau.city.bankaccount.entities.dto;

import java.math.BigDecimal;

public record AccountResponse(Long id,
                              String numberOfAccount,
                              BigDecimal amount) {
}
