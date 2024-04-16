package uk.lset.model.foriegn;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Address {
	
    private String id;
	private String line1;
	private String line2;
	private String city;
	private State state;
	private String postcode;

}
