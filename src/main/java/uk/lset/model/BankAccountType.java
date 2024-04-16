package uk.lset.model;

import java.util.List;

import org.hibernate.annotations.UuidGenerator;
import org.hibernate.envers.Audited;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uk.lset.audit.Auditable;

@Entity
@Table(name="bankaccounttype")
@Audited
@Getter @Setter @NoArgsConstructor
public class BankAccountType extends Auditable<String>{
	
	@Id
	@UuidGenerator
	@Column(name = "bankaccounttype_id")
    private String id;
	
	private String name;
	private String description;
	private Double price;
	
	@OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "bankaccounttype_id")
	private List<Review> reviews;
	
}
