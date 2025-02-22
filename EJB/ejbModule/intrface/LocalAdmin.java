package intrface;

import java.util.List;

import bean.Admin;


import jakarta.ejb.Local;

@Local
public interface LocalAdmin {
	//CRUD
	public boolean add(Admin a);
	public boolean update(Admin a);
	public boolean delete(int i);
	public Admin get(int i);
	public Admin get(String adminName);
	public List<Admin> getAll();
	
}
