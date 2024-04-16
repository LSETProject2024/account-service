package uk.lset.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.lset.model.BankAccountTransactionType;
import uk.lset.repository.BankAccountTransactionTypeRepository;

@Service
public class BankAccountTransactionTypeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountTransactionTypeService.class);
	
	@Autowired
	private BankAccountTransactionTypeRepository bankAccountTransactionTypeRepository;
	
	public BankAccountTransactionType findById(String id)  {
		return bankAccountTransactionTypeRepository.findById(id).orElse(null);
	}
	
	public BankAccountTransactionType findByType(String type)  {
		return bankAccountTransactionTypeRepository.findByType(type).orElse(null);
	}
	
	public BankAccountTransactionType save(BankAccountTransactionType bankAccountTransactionType) {
		return bankAccountTransactionTypeRepository.save(bankAccountTransactionType);
	}
	
}