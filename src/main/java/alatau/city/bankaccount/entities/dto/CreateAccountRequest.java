package alatau.city.bankaccount.entities.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateAccountRequest {

    @NotNull
    private BigDecimal initialAmount;

}
