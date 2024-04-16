package uk.lset.model.foriegn;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class State {
	
    private Long id;
	private String name;
	private Country country;
	private String wikiDataId;

}