package alatau.city.bankaccount.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

    private Long id;
    private String numberOfAccountSender;
    private String numberOfAccountReceiver;
    private BigDecimal amount;
    private LocalDateTime createdAt;

}
