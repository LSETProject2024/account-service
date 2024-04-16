package uk.lset.model;

import org.hibernate.annotations.UuidGenerator;
import org.hibernate.envers.Audited;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uk.lset.audit.Auditable;

@Entity
@Table(name="review")
@Audited
@Getter @Setter @NoArgsConstructor
public class Review extends Auditable<String>{
	
	@Id
	@UuidGenerator
	@Column(name = "review_id")
    private String id;
	
	private Long rating;
	
	private String content;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   	@JoinColumn(name = "user_id")
	private User user;
	
}
