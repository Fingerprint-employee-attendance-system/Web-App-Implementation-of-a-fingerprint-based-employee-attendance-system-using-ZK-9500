package bean;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the admins database table.
 * 
 */
@Entity
@Table(name="admins")
@NamedQuery(name="Admin.findAll", query="SELECT a FROM Admin a")
public class Admin implements Serializable {
	private static final long serialVersionUID = 1L;

	//@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	//@Column(unique=true, nullable=false)
	//private Integer id;

//	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admins_seq")
	@SequenceGenerator(name = "admins_seq", sequenceName = "admins_seq", allocationSize = 50)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	
	@Column(name="password_hash", nullable=false, length=100)
	private String passwordHash;

	@Column(nullable=false, length=50)
	private String username;

	public Admin() {
	}

	public Integer getAdminId() {
		return this.id;
	}

	public void setAdminId(Integer id) {
		this.id = id;
	}

	public String getPasswordHash() {
		return this.passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
