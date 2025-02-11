package Web.model;

public class EmployeeModel {
	    private int userId;
	    private String userName;
	    private String checkIn;
	    private String checkOut;
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getCheckIn() {
			return checkIn;
		}
		public void setCheckIn(String checkIn) {
			this.checkIn = checkIn;
		}
		public String getCheckOut() {
			return checkOut;
		}
		public void setCheckOut(String checkOut) {
			this.checkOut = checkOut;
		}
		public EmployeeModel(int userId, String userName, String checkIn, String checkOut) {
			super();
			this.userId = userId;
			this.userName = userName;
			this.checkIn = checkIn;
			this.checkOut = checkOut;
		}
		public EmployeeModel() {
			super();
		}

	    // Constructor, getters, and setters
	}

