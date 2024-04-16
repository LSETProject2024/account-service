package uk.lset.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uk.lset.model.BankAccountType;

@Repository
public interface BankAccountTypeRepository extends JpaRepository<BankAccountType, Integer> {
	Optional<BankAccountType> findById(String id);
	Optional<BankAccountType> findByName(String name);
}
