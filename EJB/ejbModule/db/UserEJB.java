package db;

import java.util.List;

import bean.Employee;
import intrface.LocalUser;
import intrface.RemoteUser;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;


@Stateless
//@TransactionManagement(TransactionManagementType.BEAN)
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public  class UserEJB implements  LocalUser ,RemoteUser {

	@PersistenceContext
	public  EntityManager em ;
 
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean add(Employee e) {
		
		try {
			
			em.persist(e);

	        return true; // Assuming success
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        return false;
	    }
		
	}

	@Override
    public boolean update(Employee e) {
		
		try {
	        Employee st = get(e.getUserId());
	        if (st != null) {
	            em.merge(e);
	            
	            return true; // Assuming success
	        } else {
	            return false; // User not found
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        return false;
	    }
    	
    }
	@Override
    public boolean delete(int userId) {
		Employee st=get(userId);
		
		em.remove(st);
		if( em.find(Employee.class, userId)== null) {
			return true;
		}else {
			return false;
		}
    }

	@Override
	public Employee get(int userId) {
	    try {
	        Employee employee = em.find(Employee.class, userId);
	        if (employee == null) {
	            throw new RuntimeException("Employee not found for userId: " + userId);
	        }
	        return employee;
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        throw new RuntimeException("Error while getting user with userId: " + userId, ex);
	    }
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> getAll() {
		Query req =em.createQuery("select s from Employee s");
		return req.getResultList();
		
	}
	@Override
	public Long getUsersCount() {
	    Query req = em.createQuery("SELECT COUNT(e) FROM Employee e");
	    return (Long) req.getSingleResult();
	}


}
