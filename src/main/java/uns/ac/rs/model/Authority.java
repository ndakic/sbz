package uns.ac.rs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Authority implements Serializable{
	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	String name;


	@JsonIgnore
	@OneToMany(mappedBy = "authority_id", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private List<UserAuthority> userAuthorities;

	public Authority() {}

	public Authority(String name) {
		this.name = name;
	}


}
