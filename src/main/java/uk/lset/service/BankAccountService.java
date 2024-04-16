package uk.lset.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.lset.model.BankAccount;
import uk.lset.repository.BankAccountRepository;

@Service
public class BankAccountService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountService.class);
	
	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	public BankAccount findById(String id)  {
		return bankAccountRepository.findById(id).orElse(null);
	}
	
	public BankAccount findByAccountNumber(Integer bankAccountNumber)  {
		return bankAccountRepository.findByAccountNumber(bankAccountNumber).orElse(null);
	}
	
	public BankAccount save(BankAccount bankAccount) {
		return bankAccountRepository.save(bankAccount);
	}
	
}