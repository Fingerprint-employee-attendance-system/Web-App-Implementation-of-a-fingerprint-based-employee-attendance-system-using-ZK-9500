  
 package db;

 import java.util.List;

import bean.Admin;

import intrface.LocalAdmin;

import intrface.RemoteAdmin;

 import jakarta.ejb.LocalBean;
 import jakarta.ejb.Stateless;
 import jakarta.ejb.TransactionAttribute;
 import jakarta.ejb.TransactionAttributeType;
 import jakarta.ejb.TransactionManagement;
 import jakarta.ejb.TransactionManagementType;
 import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
 import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;


 @Stateless
 //@TransactionManagement(TransactionManagementType.BEAN)
 @LocalBean
 @TransactionManagement(TransactionManagementType.CONTAINER)
 public  class AdminEJB implements  LocalAdmin ,RemoteAdmin {

 	@PersistenceContext
 	public  EntityManager em ;
  
 	
 	@Override
 	@TransactionAttribute(TransactionAttributeType.REQUIRED)
 	public boolean add(Admin a) {
 		
 		try {
 			
 			em.persist(a);

 	        return true; // Assuming success
 	    } catch (Exception ex) {
 	        ex.printStackTrace();
 	        return false;
 	    }
 		
 	}

 	@Override
     public boolean update(Admin a) {
 		
 		try {
 			Admin st = get(a.getAdminId());
 	        if (st != null) {
 	            em.merge(a);
 	            
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
     public boolean delete(int AdminId) {
 		Admin st=get(AdminId);
 		
 		em.remove(st);
 		if( em.find(Admin.class, AdminId)== null) {
 			return true;
 		}else {
 			return false;
 		}
     }

 	@Override
 	public Admin get(int AdminId) {
 	    try {
 	    	Admin admin = em.find(Admin.class, AdminId);
 	        if (admin == null) {
 	            throw new RuntimeException("Admin not found for adminId: " + AdminId);
 	        }
 	        return admin;
 	    } catch (Exception ex) {
 	        ex.printStackTrace();
 	        throw new RuntimeException("Error while getting user with userId: " + AdminId, ex);
 	    }
 	}
 	
 	@Override
 	public Admin get(String adminName) {
 	    try {
 	        TypedQuery<Admin> query = em.createQuery(
 	            "SELECT a FROM Admin a WHERE a.username = :username", Admin.class);
 	        query.setParameter("username", adminName);

 	        // Assuming that username is unique, use getSingleResult
 	        return query.getSingleResult();
 	    } catch (NoResultException ex) {
 	        // Handle the case when no Admin is found for the given username
 	        return null;
 	    } catch (Exception ex) {
 	        ex.printStackTrace();
 	        throw new RuntimeException("Error while getting user with username: " + adminName, ex);
 	    }
 	}


 	@SuppressWarnings("unchecked")
 	@Override
 	public List<Admin> getAll() {
 		Query req =em.createQuery("select s from Admin s");
 		return req.getResultList();
 		
 	}

 	

 }
