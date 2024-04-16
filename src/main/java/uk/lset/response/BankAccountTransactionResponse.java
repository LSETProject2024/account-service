package uk.lset.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class BankAccountTransactionResponse {
	private String id;
	private String name;
	private Long accountNumber;
	private Long sortCode;
	private List<String> transactions;
}
