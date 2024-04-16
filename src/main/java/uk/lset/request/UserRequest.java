package uk.lset.request;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class UserRequest {
	private String id;
	private String cognitoid;
	private String name;
	private Date dateofbirth;
	private String nationalinsurancenumber;
	private String address;
	private String email;
}
