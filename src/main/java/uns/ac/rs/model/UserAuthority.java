package uns.ac.rs.model;

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
public class UserAuthority {
	@Id
	@GeneratedValue
	private Long id;

	public UserAuthority() {
	}

	public UserAuthority(User user, Authority authority) {
		this.user = user;
		this.authority = authority;
	}

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private User user;
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Authority authority;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Authority getAuthority() {
		return authority;
	}

	public void setAuthority(Authority authority) {
		this.authority = authority;
	}

	@Override
	public String toString() {
		return "UserAuthority{" +
				"id=" + id +
				", user=" + user +
				", authority=" + authority +
				'}';
	}
}
