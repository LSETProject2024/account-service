package uk.lset.model;

import java.util.Date;
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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uk.lset.audit.Auditable;

@Entity
@Table(name="user")
@Audited
@Getter @Setter @NoArgsConstructor
public class User extends Auditable<String>{
	
	@Id
	@UuidGenerator
	@Column(name = "user_id")
    private String id;
	
	private String cognitoid;
	
	private String name;
	private String address;
	private String nationalinsurancenumber;
	@Column(unique=true)
	private String email;
	@Temporal(TemporalType.TIMESTAMP)
    private Date dateofbirth;
	
	@OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
	private List<BankAccount> accounts;
}
