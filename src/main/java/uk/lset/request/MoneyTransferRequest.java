package uk.lset.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class MoneyTransferRequest {
	private String id;
	private Double amount;
	private Integer fromAccount;
	private Integer fromSortCode;
	private Integer toSortCode;
	private Integer toAccount;
}
