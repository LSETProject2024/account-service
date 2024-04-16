package uk.lset.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class BankAccountRequest {
	private String id;
	private Long annualincome;
	private Long monthlyexpenses;
	private String nationalinsurancenumber;
	private String user;
	private String accounttype;
}
