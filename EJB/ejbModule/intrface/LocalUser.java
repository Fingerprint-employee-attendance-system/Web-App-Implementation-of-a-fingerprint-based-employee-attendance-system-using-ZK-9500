package intrface;

import java.util.List;

import bean.Employee;

import jakarta.ejb.Local;

@Local
public interface LocalUser {
	//CRUD
	public boolean add(Employee e);
	public boolean update(Employee e);
	public boolean delete(int i);
	public Employee get(int i);
	public List<Employee> getAll();
	public Long getUsersCount();
	
}
