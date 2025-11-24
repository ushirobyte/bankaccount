package alatau.city.bankaccount.repository;

import alatau.city.bankaccount.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(String username);

    boolean existsByUsername(String username);

}
