package uk.lset.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class ReviewRequest {
	private String id;
	private Long rating;
	private String content;
	private String user;
	private String accounttype;
}
