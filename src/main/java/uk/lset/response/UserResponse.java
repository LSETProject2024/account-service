package uk.lset.response;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class UserResponse {
	private String id;
	private String cognitoid;
	private String name;
	private Date dateofbirth;
	private String nationalinsurancenumber;
	private String address;
	private String email;
}
