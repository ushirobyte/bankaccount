package alatau.city.bankaccount.repository;

import alatau.city.bankaccount.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account, Long> {

    public Account findByNumberOfAccount(String numberOfAccount);

}
