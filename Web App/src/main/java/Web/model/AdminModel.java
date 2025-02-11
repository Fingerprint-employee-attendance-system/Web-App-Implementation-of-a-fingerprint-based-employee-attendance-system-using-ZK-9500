package Web.model;

public class AdminModel {
	private int adminId;
    private String adminName;
	public AdminModel() {
		super();
	}
	public int getAdminId() {
		return adminId;
	}
	public AdminModel(int adminId, String adminName) {
		super();
		this.adminId = adminId;
		this.adminName = adminName;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
}
