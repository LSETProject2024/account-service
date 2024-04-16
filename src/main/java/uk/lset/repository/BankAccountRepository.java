package uk.lset.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uk.lset.model.BankAccount;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {
	Optional<BankAccount> findById(String id);
	Optional<BankAccount> findByAccountNumber(Integer accountNumber);
}
