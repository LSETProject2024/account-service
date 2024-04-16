package uk.lset.model;

import org.hibernate.annotations.UuidGenerator;
import org.hibernate.envers.Audited;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uk.lset.audit.Auditable;

@Entity
@Table(name="bankaccounttransaction")
@Audited
@Getter @Setter @NoArgsConstructor @EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BankAccountTransaction extends Auditable<String>{
	
	@Id
	@UuidGenerator
	@Column(name = "bankaccounttransaction_id")
	private String id;
	private Double amount;
	private String transactionName;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "bankaccounttransactiontype_id", nullable = false)
	private BankAccountTransactionType bankAccountTransactionType;
	
}
