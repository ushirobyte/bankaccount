package alatau.city.bankaccount.service;

import alatau.city.bankaccount.entities.Account;
import alatau.city.bankaccount.entities.dto.AccountDTO;
import alatau.city.bankaccount.entities.dto.CreateAccountRequest;
import alatau.city.bankaccount.entities.dto.TransactionResponse;

import java.util.List;

public interface AccountService {

    void transfer(AccountDTO accountDTO);

    List<TransactionResponse> getTransactionResponseList(String accountNumber);

    Account createAccount(CreateAccountRequest accountRequest);

}
