package uk.lset.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uk.lset.model.BankAccount;
import uk.lset.model.BankAccountTransaction;

@Repository
public interface BankAccountTransactionRepository extends JpaRepository<BankAccountTransaction, Integer> {
	Optional<BankAccountTransaction> findById(String id);
}
