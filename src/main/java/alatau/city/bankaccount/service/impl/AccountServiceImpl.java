package alatau.city.bankaccount.service.impl;

import alatau.city.bankaccount.entities.Account;
import alatau.city.bankaccount.entities.Transaction;
import alatau.city.bankaccount.entities.dto.AccountDTO;
import alatau.city.bankaccount.entities.response.TransactionResponse;
import alatau.city.bankaccount.repository.AccountRepository;
import alatau.city.bankaccount.repository.TransactionRepository;
import alatau.city.bankaccount.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;


    @Override
    @Transactional
    public void transfer(AccountDTO accountDTO) {

        log.info("Transfer request: from={} to={} amount={}",
                accountDTO.getNumberOfAccountSender(),
                accountDTO.getNumberOfAccountReceiver(),
                accountDTO.getAmount());

        if (accountDTO.getNumberOfAccountSender().equals(accountDTO.getNumberOfAccountReceiver())) {
            throw new IllegalArgumentException("Sender and receiver accounts cannot be the same");
        }

        Account accountSender = accountRepository.findByNumberOfAccount(accountDTO.getNumberOfAccountSender());
        if (accountSender == null) {
            throw new IllegalArgumentException("Sender account not found");
        }

        Account accountReceiver = accountRepository.findByNumberOfAccount(accountDTO.getNumberOfAccountReceiver());
        if (accountReceiver == null) {
            throw new IllegalArgumentException("Receiver account not found");
        }

        if (accountSender.getAmount().compareTo(accountDTO.getAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        accountSender.setAmount(accountSender.getAmount().subtract(accountDTO.getAmount()));
        accountReceiver.setAmount(accountReceiver.getAmount().add(accountDTO.getAmount()));

        accountRepository.save(accountSender);
        accountRepository.save(accountReceiver);

        Transaction transaction = Transaction.builder()
                        .numberOfAccountSender(accountDTO.getNumberOfAccountSender())
                                .numberOfAccountReceiver(accountDTO.getNumberOfAccountReceiver())
                                        .amount(accountDTO.getAmount())
                                                .createdAt(LocalDateTime.now())
                                                        .build();

        transactionRepository.save(transaction);

        log.info("Transfer completed: from={} to={} amount={}",
                accountDTO.getNumberOfAccountSender(),
                accountDTO.getNumberOfAccountReceiver(),
                accountDTO.getAmount());
    }

    @Override
    public List<TransactionResponse> getTransactionResponseList(String accountNumber) {

        List<Transaction> transactionList = transactionRepository
                .findByNumberOfAccountSenderOrNumberOfAccountReceiver(
                        accountNumber,
                        accountNumber
                );

        return transactionList.stream()
                .map(t -> new TransactionResponse(
                        t.getId(),
                        t.getNumberOfAccountSender(),
                        t.getNumberOfAccountReceiver(),
                        t.getAmount(),
                        t.getCreatedAt()
                ))
                .toList();
    }
}
