package alatau.city.bankaccount.service;

import alatau.city.bankaccount.entities.Transaction;
import alatau.city.bankaccount.entities.dto.AccountDTO;
import alatau.city.bankaccount.entities.response.TransactionResponse;

import java.util.List;

public interface AccountService {

    void transfer(AccountDTO accountDTO);

    List<TransactionResponse> getTransactionResponseList(String accountNumber);

}
