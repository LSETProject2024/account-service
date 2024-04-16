package uk.lset.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.lset.model.BankAccountTransaction;
import uk.lset.repository.BankAccountTransactionRepository;

@Service
public class BankAccountTransactionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountTransactionService.class);
	
	@Autowired
	private BankAccountTransactionRepository bankAccountTransactionRepository;
	
	public BankAccountTransaction findById(String id)  {
		return bankAccountTransactionRepository.findById(id).orElse(null);
	}
	
	public BankAccountTransaction save(BankAccountTransaction bankAccount) {
		return bankAccountTransactionRepository.save(bankAccount);
	}
	
}