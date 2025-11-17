package alatau.city.bankaccount.repository;

import alatau.city.bankaccount.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByNumberOfAccountSenderOrNumberOfAccountReceiver(
            String numberOfAccountSender,
            String numberOfAccountReceiver
    );


}
