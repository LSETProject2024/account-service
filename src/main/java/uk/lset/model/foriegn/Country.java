package uk.lset.model.foriegn;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Country {
	
    private Long id;
	private String iso2;
	private String iso3;
	private String name;
	private String numeric_code;
	private String phonecode;
	private String capital;
	private String currency;
	private String currency_name;
	private String currency_symbol;
	private String region;
	private String subregion;
	private String timezones;
	private String emoji;
	private String emojiU;
	private String wikiDataId;
	
}