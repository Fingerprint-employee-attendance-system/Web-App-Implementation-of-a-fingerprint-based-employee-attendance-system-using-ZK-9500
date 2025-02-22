package bean;

import java.io.Serializable;
import jakarta.persistence.*;

import jakarta.persistence.Entity;

import java.util.List;


/**
 * The persistent class for the employees database table.
 * 
 */
@Entity
@Table(name="employees")
@NamedQuery(name="Employee.findAll", query="SELECT e FROM Employee e")
public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;

	
	
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	//@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
   // @Column(name = "user_id", unique = true, nullable = false)
	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employees_SEQ")
	@SequenceGenerator(name = "employees_SEQ", sequenceName = "employees_SEQ", allocationSize = 50)
	@Column(name = "user_id", unique = true, nullable = false)
	private Integer userId;

	@Column(name="template_data", nullable=false)
	private byte[] templateData;

	@Column(name="user_name", nullable=false, length=50)
	private String userName;

	//bi-directional many-to-one association to Timing
	@OneToMany(mappedBy="employee")
	private List<Timing> timings;

	public Employee() {
	}

	public Employee(Integer userId  , String userName , byte[] templateData) {
		super();
		this.userId = userId;
		this.templateData = templateData;
		this.userName = userName;
	}

	public Employee( String userName, byte[] templateData) {
		super();
		this.templateData = templateData;
		this.userName = userName;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public byte[] getTemplateData() {
		return this.templateData;
	}

	public void setTemplateData(byte[] templateData) {
		this.templateData = templateData;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<Timing> getTimings() {
		return this.timings;
	}

	public void setTimings(List<Timing> timings) {
		this.timings = timings;
	}

	public Timing addTiming(Timing timing) {
		getTimings().add(timing);
		timing.setEmployee(this);

		return timing;
	}

	public Timing removeTiming(Timing timing) {
		getTimings().remove(timing);
		timing.setEmployee(null);

		return timing;
	}

}