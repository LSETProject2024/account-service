package uk.lset.model;

import org.hibernate.annotations.UuidGenerator;
import org.hibernate.envers.Audited;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uk.lset.audit.Auditable;

@Entity
@Table(name="bankaccounttransactiontype")
@Audited
@Getter @Setter @NoArgsConstructor @EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BankAccountTransactionType extends Auditable<String>{
	
	@Id
	@UuidGenerator
	@Column(name = "bankaccounttransactiontype_id")
	private String id;
	private String type;
	
}
