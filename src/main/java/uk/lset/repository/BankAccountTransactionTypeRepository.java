package uk.lset.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uk.lset.model.BankAccountTransactionType;

@Repository
public interface BankAccountTransactionTypeRepository extends JpaRepository<BankAccountTransactionType, Integer> {
	Optional<BankAccountTransactionType> findById(String id);
	Optional<BankAccountTransactionType> findByType(String type);
}
