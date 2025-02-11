package bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import jakarta.persistence.*;

/**
 * The primary key class for the timing database table.
 * 
 */
@Embeddable
public class TimingPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="user_id", insertable=false, updatable=false, unique=true, nullable=false)
	private Integer userId;

	@Temporal(TemporalType.DATE)
	@Column(name="user_day", unique=true, nullable=false)
	private java.util.Date userDay;

	public TimingPK() {
	}
	
	public TimingPK(Integer userId, Date userDay) {
		super();
		
		 if (userDay == null) {
	            // Set the checkinoutday to a default value or obtain it as needed
	            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	            long timeInMillis = timestamp.getTime();
	            Date date = new Date(timeInMillis);
	            setUserDay(date);
	            userDay = date;
	        }
		 
		this.userId = userId;
		this.userDay = userDay;
	
	}
	
	public Integer getUserId() {
		return this.userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public java.util.Date getUserDay() {
		return this.userDay;
	}
	public void setUserDay(java.util.Date userDay) {
		this.userDay = userDay;
	}

	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TimingPK)) {
			return false;
		}
		TimingPK castOther = (TimingPK)other;
		return 
			this.userId.equals(castOther.userId)
			&& this.userDay.equals(castOther.userDay);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId.hashCode();
		hash = hash * prime + this.userDay.hashCode();
		
		return hash;
	}
}