package intrface;


import java.sql.Date;
import java.util.List;

import bean.Timing;
import bean.TimingPK;
import jakarta.ejb.Remote;

@Remote
public interface RemoteTiming {
	//CRUD
		public boolean add(Timing usercheckinout);
		public boolean update(Timing usercheckinout);
		public boolean delete(TimingPK pk);
		public Timing get(TimingPK pk);
		public boolean deleteAll(TimingPK pk);
		public List<Timing> getAll(TimingPK pk) ;
		public int getUserInCountInRange(Date start, Date end);
}
