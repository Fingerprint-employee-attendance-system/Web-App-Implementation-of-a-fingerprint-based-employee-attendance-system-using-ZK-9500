package bean;

import java.io.Serializable;
import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;


/**
 * The persistent class for the timing database table.
 * 
 */
@Entity
@Table(name="timing")
@NamedQuery(name="Timing.findAll", query="SELECT t FROM Timing t")
public class Timing implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TimingPK id;

	@Column(name="user_in")
	private Time userIn;

	@Column(name="user_out")
	private Time userOut;

	//bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false, insertable=false, updatable=false)
	private Employee employee;

	public Timing() {
	}

	public Timing(TimingPK id) {
		super();
		this.id = id;
	}
	public Timing(Time userIn, Time userOut, Date userDay) {
		super();
		this.userIn = userIn;
		this.userIn = userIn;
		this.id = new TimingPK();
		id.setUserDay(userDay);
	}
	public TimingPK getId() {
		return this.id;
	}

	public void setId(TimingPK id) {
		this.id = id;
	}

	public Time getUserIn() {
		return this.userIn;
	}

	public void setUserIn(Time userIn) {
		this.userIn = userIn;
	}

	public Time getUserOut() {
		return this.userOut;
	}

	public void setUserOut(Time userOut) {
		this.userOut = userOut;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}