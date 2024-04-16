package uk.lset.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class BankAccountTransactionRequest {
	private String id;
	private Double amount;
	private String transactionName;
	private Integer accountNumber;
	private String transactionType;
}
