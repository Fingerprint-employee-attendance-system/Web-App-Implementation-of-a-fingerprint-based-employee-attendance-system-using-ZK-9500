package main;

import java.io.Serializable;
import java.sql.Time;






public class Employee implements Serializable {
	public Employee(Integer userId  , String userName, byte[] templateData) {
		super();
		this.userId = userId;
		this.templateData = templateData;
		this.userName = userName;
	}

	public Employee() {
		super();
	}

	private static final long serialVersionUID = 1L;

	
	private Integer userId;

	
	private String checkIn;

	
	private String checkOut;

	
	private byte[] templateData;


	private String userName;

	public Employee(String userName, byte[] templateData, String checkIn) {
		super();

		this.checkIn = checkIn;
		this.templateData = templateData;
		this.userName = userName;
	}

	public Employee(Integer userId, String userName, byte[] templateData, String checkIn, String checkOut) {
		super();

		this.userId = userId;
		this.checkIn = checkIn;
		this.templateData = templateData;
		this.userName = userName;
		this.checkOut = checkOut;
	}

	public Employee(String userName, byte[] templateData, String checkIn, String checkOut) {
		super();

		this.checkIn = checkIn;
		this.templateData = templateData;
		this.userName = userName;
		this.checkOut = checkOut;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCheckIn() {
		return this.checkIn;
	}

	public void setCheckIn(String String) {
		this.checkIn = String;
	}

	public String getCheckOut() {
		return this.checkOut;
	}

	public void setCheckOut(String string) {
		this.checkOut = string;
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

}