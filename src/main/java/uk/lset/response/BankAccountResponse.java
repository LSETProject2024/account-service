package uk.lset.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import uk.lset.model.BankAccountType;

@Getter @Setter 
public class BankAccountResponse {
	private String id;
	private Integer accountNumber;
	private Integer sortCode;
	private String user;
	private BankAccountType bankAccountType;
	private List<String> transactions;
}
