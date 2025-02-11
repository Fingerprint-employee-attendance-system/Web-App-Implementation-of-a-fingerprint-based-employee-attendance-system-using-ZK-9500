package Web;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import Web.model.AdminModel;
import Web.model.EmployeeModel;
import bean.Admin;
import bean.Employee;
import bean.Timing;
import bean.TimingPK;
import intrface.LocalAdmin;
import intrface.LocalTiming;
import intrface.LocalUser;
import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @EJB
	private LocalUser localUser;
	
	@EJB
	private LocalTiming timing;
	
	@EJB
	private LocalAdmin localAdmin;
	


	    
	private TimingPK pk;
	 private  final int COST_FACTOR = 12;
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	// Check if the session exists and contains an admin
        HttpSession session = request.getSession(false); // Do not create a new session if it doesn't exist
        if (session == null || session.getAttribute("loggedInAdmin") == null) {
            // If no session or no admin in the session, redirect to the login page
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        
        
     
        
     
        
        
        request.setAttribute("UsersCount", localUser.getUsersCount());
        request.setAttribute("Today", countForToday());
        request.setAttribute("Yesterday", countForYesterday());
        request.setAttribute("ThisWeek", countForThisWeek());
       request.setAttribute("LastWeek", countForLastWeek());
        request.setAttribute("ThisMonth", countForThisMonth());
        request.setAttribute("LastMonth", countForLastMonth());
        
        
        request.setAttribute("todayArrow", getArrowHTML(countForToday(),countForYesterday()));
        request.setAttribute("weekArrow", getArrowHTML(countForThisWeek(),countForLastWeek()));
        request.setAttribute("monthArrow", getArrowHTML(countForThisMonth(),countForLastMonth()));
     // Check if the parameter is "getUserDetails"
        String action = request.getParameter("action");
        
        
        if ("GetEmployeeLists".equals(action)){
        	//employee list
        	// userDetails( request,  response);
        	employeeList(request,response);
        	
        }
        
        // if ("getUserDetails".equals(action)) {
           // userDetails( request,  response);
        //	processUserDetails( request,  response);
            
      //  } 
        
        
          if ("GetAdminsLists".equals(action)){
        	
        	 // admins list 
            
            adminList(request);
           
        }
    	
        
        
        
       
        
        // Forward the request to dashboard.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/dashboard.jsp");
        dispatcher.forward(request, response);
    }



    private String getArrowHTML(int firstNumber, int secondNumber) {
        int percentage;

        if (firstNumber > secondNumber) {
            if (secondNumber == 0) {
                percentage = 100; 
            } else {
                percentage = ((firstNumber - secondNumber) * 100) / secondNumber;
            }
            return "<i class=\"arrow up\"></i> " + "&nbsp;&nbsp; +"+ percentage + "% ";
        } else if (secondNumber > firstNumber) {
            if (firstNumber == 0) {
                percentage =  100; 
            } else {
                percentage = ((secondNumber - firstNumber) * 100) / firstNumber;
            }
            return "<i class=\"arrow down\"></i> " +"&nbsp;&nbsp; -"+ percentage + "%";
        } else {
            return "";
        }
    }





	private int countForToday() {
		LocalDate today = LocalDate.now();
        Date startDate = java.sql.Date.valueOf(today.atStartOfDay().toLocalDate());
        Date endDate = java.sql.Date.valueOf(today.atTime(LocalTime.MAX).toLocalDate());

        return timing.getUserInCountInRange(startDate, endDate);
		 
	}
	

private int countForYesterday() {
    LocalDate yesterday = LocalDate.now().minusDays(1);
    Date startDate = java.sql.Date.valueOf(yesterday.atStartOfDay().toLocalDate());
    Date endDate = java.sql.Date.valueOf(yesterday.atTime(LocalTime.MAX).toLocalDate());

    return timing.getUserInCountInRange(startDate, endDate);
}

private int countForThisWeek() {
    LocalDate startOfWeek = LocalDate.now().with(DayOfWeek.MONDAY);
    Date startDate = java.sql.Date.valueOf(startOfWeek.atStartOfDay().toLocalDate());
    Date endDate = java.sql.Date.valueOf(LocalDate.now().atTime(LocalTime.MAX).toLocalDate());

    return timing.getUserInCountInRange(startDate, endDate);
}

private int countForLastWeek() {
    LocalDate startOfLastWeek = LocalDate.now().minusWeeks(1).with(DayOfWeek.MONDAY);
    LocalDate endOfLastWeek = LocalDate.now().minusWeeks(1).with(DayOfWeek.SUNDAY);

    Date startDate = java.sql.Date.valueOf(startOfLastWeek.atStartOfDay().toLocalDate());
    Date endDate = java.sql.Date.valueOf(endOfLastWeek.atTime(LocalTime.MAX).toLocalDate());

    return timing.getUserInCountInRange(startDate, endDate);
}

private int countForThisMonth() {
    LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
    Date startDate = java.sql.Date.valueOf(startOfMonth.atStartOfDay().toLocalDate());
    Date endDate = java.sql.Date.valueOf(LocalDate.now().atTime(LocalTime.MAX).toLocalDate());

    return timing.getUserInCountInRange(startDate, endDate);
}

private int countForLastMonth() {
    LocalDate startOfLastMonth = LocalDate.now().minusMonths(1).withDayOfMonth(1);
    LocalDate endOfLastMonth = LocalDate.now().minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());

    Date startDate = java.sql.Date.valueOf(startOfLastMonth.atStartOfDay().toLocalDate());
    Date endDate = java.sql.Date.valueOf(endOfLastMonth.atTime(LocalTime.MAX).toLocalDate());

    return timing.getUserInCountInRange(startDate, endDate);
}







	private void adminList(HttpServletRequest request) {
    	List<Admin> admins = localAdmin.getAll();
        List<AdminModel> adminList = new ArrayList<>();
	for (Admin admin : admins) {
		 System.out.println("Processing admin: " + admin.getAdminId() + ", admin name: " + admin.getUsername() );
		AdminModel newAdmin = new AdminModel();
		newAdmin.setAdminId(admin.getAdminId());
		newAdmin.setAdminName(admin.getUsername());
		
		adminList.add(newAdmin);
			
			 
            
        }
        request.setAttribute("adminList", adminList);
		
	}

	private void processUserDetails(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    String userIdParameter = request.getParameter("userId");
	    
	    
	 // Handle GetEmployeeLists logic
	    List<Employee> employees = localUser.getAll();
	    List<EmployeeModel> userList = new ArrayList<>();
	    for (Employee employee : employees) {
	        pk = new TimingPK(employee.getUserId(), null);
	        Timing inout = timing.get(pk);
	        System.out.println("Processing employee: " + employee.getUserId() + ", CheckIn: " + inout.getUserIn() + ", CheckOut: " + inout.getUserOut());
	        String checkIn = inout.getUserIn() != null ? inout.getUserIn().toString() : "N/A";
	        String checkOut = inout.getUserOut() != null ? inout.getUserOut().toString() : "N/A";
	        EmployeeModel model = new EmployeeModel(employee.getUserId(), employee.getUserName(), checkIn, checkOut);

	        userList.add(model);
	    }

	    // Set the user list as an attribute in the request
	    request.setAttribute("userList", userList);
	    
	    
	    // Handle getUserDetails logic
	    int userId = Integer.parseInt(userIdParameter);
        if (localUser.get(userId) != null) {
            TimingPK pk = new TimingPK(userId, null);
            List<Timing> detailsList = timing.getAll(pk);

            // Set the detailsList as an attribute in the request
            request.setAttribute("detailsList", detailsList);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid user ID");
        }

	    
	}

	private void employeeList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userIdParameter = request.getParameter("userId");

        if (userIdParameter != null && !userIdParameter.isEmpty()) {
        	processUserDetails(request,response);
		//	userDetails(request,response);
			
			}else 
			{
				
    	List<Employee> employees = localUser.getAll();
    	List<EmployeeModel> userList = new ArrayList<>();
    	for (Employee employee : employees) {
        	
    		
        	pk = new TimingPK(employee.getUserId(),null) ;
        	
        //	pkEJB = new UsercheckinoutPK(employee.getUserId(),getCurrentDate()) ;
            Timing inout = timing.get(pk) ;
			 System.out.println("Processing employee: " + employee.getUserId() + ", CheckIn: " + inout.getUserIn() + ", CheckOut: " + inout.getUserOut());
			 String checkIn = inout.getUserIn() != null ? inout.getUserIn().toString() : "N/A";
			 String checkOut = inout.getUserOut() != null ? inout.getUserOut().toString() : "N/A";
			 EmployeeModel model = new EmployeeModel (employee.getUserId(),employee.getUserName(),checkIn,checkOut);
            
                    userList.add(model);
            
        }
    	
        // Set the user list as an attribute in the request
        request.setAttribute("userList", userList);
			}	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("addAdmin".equals(action)) {
            // Get parameters for adding admin
            String username = request.getParameter("adminUsername");
            String password = request.getParameter("adminPassword");
            
        if(localAdmin.get(username)== null) {
            // save admin
             String pass = hash(password);
            Admin admin = new Admin();
             admin.setUsername(username);
             admin.setPasswordHash(pass);
             localAdmin.add(admin);
        }
             
           response.sendRedirect(request.getContextPath() + "/DashboardServlet");
       
        } 
        
        else if ("exit".equals(action)) {
            // Handle exit action
            HttpSession session = request.getSession();
            session.invalidate(); // Invalidate the session to logout the user

            // Redirect to the index page
            response.sendRedirect(request.getContextPath() + "/");
            return; // Return to avoid further processing
        } 
        else if ("deleteAdmin".equals(action)) {
            // Handle delete admin action
            String adminIdToDelete = request.getParameter("adminIdToDelete");
            
            if (adminIdToDelete != null && !adminIdToDelete.isEmpty()) {
            	int adminId = Integer.parseInt(adminIdToDelete);
                if(localAdmin.get(adminId) != null) {
                
                localAdmin.delete(adminId);
            }}

            // Redirect to refresh the dashboard
            response.sendRedirect(request.getContextPath() + "/DashboardServlet");
            return; // Return to avoid further processing
        }
        else {
            // Handle other actions or perform default logic
            doGet(request, response);
        }
    }
    public  String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(COST_FACTOR));
    }
   
    
    //statistics
    
   

  
}
