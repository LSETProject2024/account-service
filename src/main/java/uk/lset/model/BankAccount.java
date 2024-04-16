package uk.lset.model;

import java.util.List;

import org.hibernate.annotations.UuidGenerator;
import org.hibernate.envers.Audited;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uk.lset.audit.Auditable;

@Entity
@Table(name="bankaccount")
@Audited
@Getter @Setter @NoArgsConstructor
public class BankAccount extends Auditable<String>{
	
	@Id
	@UuidGenerator
	@Column(name = "bankaccount_id")
    private String id;
	
	private Integer accountNumber;
	private Integer sortCode;
	
	private Double balance;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "bankaccounttype_id", nullable = false)
	private BankAccountType bankAccountType;
	
	@OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "bankaccount_id")
	private List<BankAccountTransaction> transactions;
	
	
}
