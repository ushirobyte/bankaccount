package alatau.city.bankaccount;

import alatau.city.bankaccount.entities.Account;
import alatau.city.bankaccount.entities.dto.AccountDTO;
import alatau.city.bankaccount.repository.AccountRepository;
import alatau.city.bankaccount.repository.TransactionRepository;
import alatau.city.bankaccount.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTestImpl {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void transfer_success() {
        AccountDTO dto = new AccountDTO();
        dto.setNumberOfAccountSender("1111");
        dto.setNumberOfAccountReceiver("2222");
        dto.setAmount(BigDecimal.valueOf(100));

        Account sender = new Account();
        sender.setNumberOfAccount("1111");
        sender.setAmount(BigDecimal.valueOf(500));

        Account receiver = new Account();
        receiver.setNumberOfAccount("2222");
        receiver.setAmount(BigDecimal.valueOf(200));

        when(accountRepository.findByNumberOfAccount("1111")).thenReturn(sender);
        when(accountRepository.findByNumberOfAccount("2222")).thenReturn(receiver);

        accountService.transfer(dto);

        assertEquals(BigDecimal.valueOf(400), sender.getAmount());
        assertEquals(BigDecimal.valueOf(300), receiver.getAmount());

        verify(accountRepository, times(1)).save(sender);
        verify(accountRepository, times(1)).save(receiver);
        verify(transactionRepository, times(1)).save(any());
    }

    @Test
    void transfer_throwsException_whenSenderAndReceiverAreTheSame() {
        AccountDTO dto = new AccountDTO();
        dto.setNumberOfAccountSender("1111");
        dto.setNumberOfAccountReceiver("1111");
        dto.setAmount(BigDecimal.valueOf(100));

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> accountService.transfer(dto)
        );

        assertEquals("Sender and receiver accounts cannot be the same", ex.getMessage());
        verifyNoInteractions(accountRepository);
    }

    @Test
    void transfer_throwsException_whenSenderAccountNotFound() {
        AccountDTO dto = new AccountDTO();
        dto.setNumberOfAccountSender("1111");
        dto.setNumberOfAccountReceiver("2222");
        dto.setAmount(BigDecimal.valueOf(100));

        when(accountRepository.findByNumberOfAccount("1111")).thenReturn(null);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> accountService.transfer(dto)
        );

        assertEquals("Sender account not found", ex.getMessage());
        verify(accountRepository, times(1)).findByNumberOfAccount("1111");
        verify(accountRepository, never()).save(any());
        verify(transactionRepository, never()).save(any());
    }

    @Test
    void transfer_throwsException_whenInsufficientBalance() {
        AccountDTO dto = new AccountDTO();
        dto.setNumberOfAccountSender("1111");
        dto.setNumberOfAccountReceiver("2222");
        dto.setAmount(BigDecimal.valueOf(1000));

        Account sender = new Account();
        sender.setNumberOfAccount("1111");
        sender.setAmount(BigDecimal.valueOf(200));

        Account receiver = new Account();
        receiver.setNumberOfAccount("2222");
        receiver.setAmount(BigDecimal.valueOf(500));

        when(accountRepository.findByNumberOfAccount("1111")).thenReturn(sender);
        when(accountRepository.findByNumberOfAccount("2222")).thenReturn(receiver);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> accountService.transfer(dto)
        );

        assertEquals("Insufficient balance", ex.getMessage());

        verify(accountRepository, never()).save(any());
        verify(transactionRepository, never()).save(any());
    }
}

