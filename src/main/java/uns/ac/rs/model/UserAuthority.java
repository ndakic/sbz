package uns.ac.rs.model;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Mapping table between User and Authority
 *
 */
@Entity
@Data
public class UserAuthority {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	private User user_id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Authority authority_id;


	public UserAuthority() {
	}

	public UserAuthority(User user_id, Authority authority_id) {
		this.user_id = user_id;
		this.authority_id = authority_id;
	}

	@Override
	public String toString() {
		return "UserAuthority{" +
				", authority=" + authority_id.getName() +
				"user_id=" + user_id.getUsername()+
				'}';
	}
}
