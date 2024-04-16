package uk.lset.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.lset.model.BankAccountType;
import uk.lset.repository.BankAccountTypeRepository;

@Service
public class BankAccountTypeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountTypeService.class);
	
	@Autowired
	private BankAccountTypeRepository bankAccountTypeRepository;
	
	public BankAccountType findById(String id)  {
		return bankAccountTypeRepository.findById(id).orElse(null);
	}
	
	public List<BankAccountType> findAll(){
		return bankAccountTypeRepository.findAll();
	}
	
	public BankAccountType findByName(String name)  {
		return bankAccountTypeRepository.findByName(name).orElse(null);
	}
	
	public BankAccountType save(BankAccountType bankAccountType) {
		return bankAccountTypeRepository.save(bankAccountType);
	}
	
}