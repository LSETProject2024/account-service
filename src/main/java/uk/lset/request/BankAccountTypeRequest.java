package uk.lset.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class BankAccountTypeRequest {
	private String id;
	private String name;
	private String description;
}
