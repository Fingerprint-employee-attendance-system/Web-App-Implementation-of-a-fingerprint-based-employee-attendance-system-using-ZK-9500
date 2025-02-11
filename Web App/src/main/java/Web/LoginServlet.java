package Web;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

import bean.Admin;
import intrface.LocalAdmin;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	
	
	@EJB
	private LocalAdmin localAdmin;
	

	 private  final int COST_FACTOR = 12;
	 
	private static final long serialVersionUID = 1L;
        private String errorMsg;
        InetAddress ip;
	
	  protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	            throws ServletException, IOException {
		  
		  HttpSession session = request.getSession();
		    if (session.getAttribute("loggedInAdmin") != null) {
		        response.sendRedirect("DashboardServlet?action=GetEmployeeLists");
		    } else {
		        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/index.jsp");
		        dispatcher.forward(request, response);
		    }
		    
	       
	    }
	  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 try {
	            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
	            while (interfaces.hasMoreElements()) {
	                NetworkInterface networkInterface = interfaces.nextElement();
	                if (networkInterface.isUp() && !networkInterface.isLoopback()) {
	                    Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
	                    while (addresses.hasMoreElements()) {
	                        InetAddress address = addresses.nextElement();
	                        if (address instanceof Inet4Address) {
	                            String ipAddress = address.getHostAddress();
	                            // Do something with the IP address, like setting it in a session attribute
	                            request.getSession().setAttribute("webIp", ipAddress);
	                            
	                        }
	                    }
	                }
	            }
	        } catch (SocketException e) {
	            e.printStackTrace();
	        }
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (isValidAdmin(request,username, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedInAdmin", username);
           response.sendRedirect("DashboardServlet?action=GetEmployeeLists");
       } else {
           response.sendRedirect(request.getContextPath() + "/");
        }
    }


    
    public  String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(COST_FACTOR));
    }

   
    public  boolean verify(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
    
	  private boolean isValidAdmin(HttpServletRequest request ,String username, String password) {
		  HttpSession session = request.getSession();
		  
		  
	        if(password.isEmpty()) {
	        	
	        	errorMsg = "password is Empty";
	        	session.setAttribute("errorMsg", errorMsg);
	        	return false;
	        	
	        }else if(password.isEmpty() && username.isEmpty() ) {
	        	
	        	
	        	
	        	errorMsg = "both password and username are Empty";
	        	session.setAttribute("errorMsg", errorMsg);
	        	return false;
	        	
	        	
	        }else if(username.isEmpty() ) {
	        	
	        	
	        	errorMsg = "username is Empty";
	        	session.setAttribute("errorMsg", errorMsg);
	        	return false;
	        	
	        	
	        } else {
	        	
	        	Admin admin = localAdmin.get(username);
	        	
	        	 String passHash = passHash(username);
	        	 
	        	if (admin == null || !admin.getUsername().equals(username)) {
	        		
	        		
	        		 errorMsg = "Admin with username " + username + " not found";
	                 session.setAttribute("errorMsg", errorMsg);
	                 return false;
	        	}
	        	
	        	if (!verify(password, passHash)) {
	                errorMsg = "Incorrect password";
	                session.setAttribute("errorMsg", errorMsg);
	                return false;
	            }
	        	if (verify(password, passHash)) {
	        		
	                return true;
	            }
	        	
	        }
	        
	        return false;
	    }

	    private String passHash(String adminName) {
	    	Admin admin = localAdmin.get(adminName);
	    	if(admin != null) {
	    	return admin.getPasswordHash();	
	    		
	    		
	    	}else {
	    		return null;
	    	}
	        
	    }
}

