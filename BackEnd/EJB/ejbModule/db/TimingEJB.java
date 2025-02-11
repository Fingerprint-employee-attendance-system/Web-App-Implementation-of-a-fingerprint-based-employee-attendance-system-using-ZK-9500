package db;

import java.sql.Date;
import java.sql.Timestamp;

import java.util.List;

import bean.Timing;
import bean.TimingPK;

import intrface.LocalTiming;
import intrface.RemoteTiming;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

@Stateless
//@TransactionManagement(TransactionManagementType.BEAN)
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class TimingEJB  implements  LocalTiming ,RemoteTiming {
	
	@PersistenceContext
	public  EntityManager em ;

	
	@Override
	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean add(Timing timing) {
	    try {
	        // Ensure that the UsercheckinoutPK has a valid checkinoutday value
	    	TimingPK pk = timing.getId();
	        if (pk != null && pk.getUserDay() == null) {
	            // Set the checkinoutday to a default value or obtain it as needed
	            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	            long timeInMillis = timestamp.getTime();
	            Date date = new Date(timeInMillis);
	            pk.setUserDay(date);
	        }

	        em.persist(timing);
	        return true; // Assuming success
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        return false;
	    }
	}


	@Override
	public boolean update(Timing timing) {
		try {
			Timing st = get(timing.getId());
	        if (st != null) {
	            em.merge(timing);
	            
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
	public boolean delete(TimingPK pk) {
		Timing st= get(pk);
		
		em.remove(st);
		if( em.find(Timing.class, pk)== null) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean deleteAll(TimingPK pk) {
	    try {
	        Query query = em.createQuery("DELETE FROM Timing t WHERE t.id.userId = :employeeId");
	        query.setParameter("employeeId", pk.getUserId());
	        int rowsAffected = query.executeUpdate();
	        return rowsAffected > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}




	@Override
	public Timing get(TimingPK pk) {
	    Timing st = em.find(Timing.class, pk);
	    
	    if (st == null) {
	        // Create a new Usercheckinout object with null values
	        return new Timing(pk);
	    }

	    return st;
	}


	@Override
	public List<Timing> getAll(TimingPK pk) {
	    try {
	        TypedQuery<Timing> query = em.createQuery(
	                "SELECT new bean.Timing(c.userIn, c.userOut, c.id.userDay) " +
	                        "FROM Timing c WHERE c.id.userId = :employeeId", Timing.class);
	        query.setParameter("employeeId", pk.getUserId());
	        return query.getResultList();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null; // Handle this appropriately in your application
	    }
	}

	@Override
	public int getUserInCountInRange(Date start, Date end) {
		try {
	        TypedQuery<Long> query = em.createQuery(
	                "SELECT COUNT(c) FROM Timing c " +
	                        "WHERE c.userIn IS NOT NULL AND c.id.userDay BETWEEN :start AND :end", Long.class);
	        query.setParameter("start", start);
	        query.setParameter("end", end);
	        return Math.toIntExact(query.getSingleResult());
	    } catch (Exception e) {
	        e.printStackTrace();
	        return 0; // Handle this appropriately in your application
	    }
	}


}
