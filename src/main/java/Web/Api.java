package Web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import bean.Employee;
import bean.Timing;
import bean.TimingPK;
import intrface.LocalTiming;
import intrface.LocalUser;
import jakarta.ejb.EJB;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Api/*")
public class Api extends HttpServlet {
	    @EJB
	    private LocalUser localUser; 
	    
	    @EJB
		private LocalTiming localTiming;

	    private TimingPK timingPK;
	    
	    boolean saveSuccess;
	    int userId;
	private static final long serialVersionUID = 2114630366435358155L;

	 protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
		 String pathInfo = request.getPathInfo();
		 if (pathInfo != null) {
		     // Proceed with processing pathInfo
		     String[] pathSegments = pathInfo.split("/");

		        // Extract userId from the path, if present
		         userId =Integer.parseInt((pathSegments.length > 1) ? pathSegments[1] : null) ;
		     
		 } 

	        


	        String operation = request.getParameter("operation");
		 
		 try {
	            String apiKey = request.getHeader("ubermenschios777");

	            // Check if the API key is valid
	            if (!isValidApiKey(apiKey)) {
	                response.setContentType("application/json");
	                response.getWriter().write("{\"status\": \"error\", \"message\": \"Invalid API key.\"}");
	                return;
	            }
	        if (operation != null) {
	            switch (operation) {

	            case "GetUser":
	            	GetUser(request,response,userId);
	                break;
	            case "GetAllUsers":
	            	GetAllUsers(request,response);
	                break; 
	            case "getDetails":
	            	getDetails(request,response,userId);
	                break; 
	                
	                // Add more cases for other operations
	                default:
	                    // Handle unknown operation
	                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown operation");
	                    break;
	            }
	        } else {
	            // Handle case where "operation" parameter is missing
	            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing operation parameter");
	        }
		 }
	     catch (Exception ex) {
	        ex.printStackTrace();
	        response.setContentType("application/json");
	        response.getWriter().write("{\"status\": \"error\", \"message\": \"Internal server error.\"}");
	    }
		    
	    }
   

  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// if (!request.isSecure()) {
        // Handle the case when the request is not using HTTPS
       // response.setContentType("application/json");
     //   response.getWriter().write("{\"error\": \"HTTPS is required for this resource.\"}");
   //     return;
 //   }
		String pathInfo = request.getPathInfo();
		if (pathInfo != null) {
		     // Proceed with processing pathInfo
		     String[] pathSegments = pathInfo.split("/");

		        // Extract userId from the path, if present
		         userId =Integer.parseInt((pathSegments.length > 1) ? pathSegments[1] : null) ;
		     
		 }  

        String operation = request.getParameter("operation");
	    
	    try {
            String apiKey = request.getHeader("ubermenschios777");

            // Check if the API key is valid
            if (!isValidApiKey(apiKey)) {
                response.setContentType("application/json");
                response.getWriter().write("{\"status\": \"error\", \"message\": \"Invalid API key.\"}");
                return;
            }

            if (operation != null) {
    	        switch (operation) {
    	            case "AddUser":
    	            	AddUser(request,response);
    	                break;
    	            case "UpdateUser":
    	            	UpdateUser(request,response);
    	                break;
    	             
    	            
    	            // Add more cases for other operations
    	            default:
    	                // Handle unknown operation
    	                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown operation");
    	                break;
    	        }
    	    } else {
    	        // Handle case where "operation" parameter is missing
    	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing operation parameter");
    	    }
    }
     catch (Exception ex) {
        ex.printStackTrace();
        response.setContentType("application/json");
        response.getWriter().write("{\"status\": \"error\", \"message\": \"Internal server error.\"}");
    }
	   
	}
	
	@Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		if (pathInfo != null) {
		     // Proceed with processing pathInfo
		     String[] pathSegments = pathInfo.split("/");

		        // Extract userId from the path, if present
		         userId =Integer.parseInt((pathSegments.length > 1) ? pathSegments[1] : null) ;
		     
		 } 

        String operation = request.getParameter("operation");
	    
	    try {
            String apiKey = request.getHeader("ubermenschios777");

            // Check if the API key is valid
            if (!isValidApiKey(apiKey)) {
                response.setContentType("application/json");
                response.getWriter().write("{\"status\": \"error\", \"message\": \"Invalid API key.\"}");
                return;
            }

            if (operation != null) {
    	        switch (operation) {
    	            
    	            case "DeleteUser":
    	            	DeleteUser(request,response,userId);
    	                break; 
    	            
    	            // Add more cases for other operations
    	            default:
    	                // Handle unknown operation
    	                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown operation");
    	                break;
    	        }
    	    } else {
    	        // Handle case where "operation" parameter is missing
    	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing operation parameter");
    	    }
    }
     catch (Exception ex) {
        ex.printStackTrace();
        response.setContentType("application/json");
        response.getWriter().write("{\"status\": \"error\", \"message\": \"Internal server error.\"}");
    }
    }
	
	
	
	private void AddUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
	      // Read the JSON payload from the request
      BufferedReader reader = request.getReader();
      StringBuilder jsonPayload = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
          jsonPayload.append(line);
      }

      // Parse the JSON payload
      JsonReader jsonReader = Json.createReader(new StringReader(jsonPayload.toString()));
      JsonObject userJson = jsonReader.readObject();

      // Retrieve parameters from the JSON object
      String userName = userJson.getString("username");
      String templateDataStr = userJson.getString("templateData");

      // Convert the Base64 string to byte array
      byte[] templateData = Base64.getDecoder().decode(templateDataStr);
     // int id = userJson.getInt("id");
      // Retrieve and parse the checkIn parameter
     // String checkInStr = userJson.getString("checkIn");
    //  Timestamp checkInTimestamp = Timestamp.valueOf(checkInStr);

      
   //   Employee employee =  new Employee(id,userName, templateData);
  //   pkEJB = new TimingPK(id,null);
      Employee employee =  new Employee(userName, templateData);
      Timing inout = new Timing();
      inout.setId(timingPK);
      // Save the user using your service
    //  if (localUser.add(employee) && usercheckinout.add(inout) ) {
      if (localUser.add(employee)  ) {
          saveSuccess = true;
      } else {
          saveSuccess = false;
      }

      // Send the response
      response.setContentType("application/json");
      PrintWriter writer = response.getWriter();

      if (saveSuccess) {
          writer.write("{\"status\": \"success\"}");
      } else {
          writer.write("{\"status\": \"error\", \"message\": \"Failed to save user.\"}");
      }

      writer.flush();
	}

	private void UpdateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	// Read the JSON payload from the request
        String jsonPayload = readJsonPayload(request);

        JsonObject userJson = parseJson(jsonPayload);

        int userId = userJson.getInt("id");
        timingPK = new TimingPK(userId, null);

        Timing timing = localTiming.get(timingPK);
        if (timing == null) {
            timing = new Timing(timingPK);
        }

        updateTimeFromJson(userJson, "checkIn", timing::setUserIn);
        updateTimeFromJson(userJson, "checkOut", timing::setUserOut);


       
        
        boolean saveSuccess = localTiming.update(timing);

        sendUpdateResponse(response, saveSuccess);
		
	}
	
    private void GetUser(HttpServletRequest request, HttpServletResponse response,int userId) throws IOException {
   	 String pathInfo = request.getPathInfo();
   	 if (pathInfo != null) {
   		 try {
   			 
   		        if (localUser.get(userId) != null) {
   		        	 timingPK = new TimingPK(userId,null) ;
   		        		
   		        	
   		            
   		            Timing inout = localTiming.get(timingPK) ;
   		            
   		        	 byte[] templateData = localUser.get(userId).getTemplateData();
   		             Time checkInTime = inout.getUserIn();
   		             Time checkOutTime = inout.getUserOut();

   		             SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");      
   		             String checkIn = checkInTime != null ? dateFormat.format(checkInTime) : "N/A";
   		             String checkOut = checkOutTime != null ? dateFormat.format(checkOutTime) : "N/A";

   		             JsonObject userJson = Json.createObjectBuilder()
   		                     .add("userId", localUser.get(userId).getUserId())
   		                     .add("userName", localUser.get(userId).getUserName())
   		                     .add("CheckIn", checkIn)
   		                     .add("CheckOut", checkOut)
   		                     .add("templateData", Base64.getEncoder().encodeToString(templateData))
   		                     // Add more attributes as needed
   		                     .build();

   		             response.setContentType("application/json");
   		             PrintWriter writer = response.getWriter();
   		             writer.write(userJson.toString());
   		             writer.flush();
   		        } else {
   		            // Handle the case when the user is not found
   		        	response.setContentType("application/json");
   		            response.getWriter().write("{\"error\": \"User not found.\"}");
   		        }
               
            } catch (NumberFormatException e) {
                // Handle invalid user ID format
            	 response.setContentType("application/json");
                 response.getWriter().write("{\"error\": \"Invalid User ID format.\"}");
            }
   	
   	 }else {
            // Handle the case when userId parameter is missing in the path
        	 response.setContentType("application/json");
             response.getWriter().write("{\"error\": \"userId parameter is missing in the path.\"}");
        }
	}
	
    private void DeleteUser(HttpServletRequest request, HttpServletResponse response,int userId) throws IOException {
 	   String pathInfo = request.getPathInfo();
        if (pathInfo != null) {
            try {
                
               
                if (localUser.get(userId) != null) {
                timingPK = new TimingPK(userId, null);
                	
                	localTiming.deleteAll(timingPK);
                	localUser.delete(userId);
                	
                	
                	
                	response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"User not found.\"}");
                } else {
                    // Handle the case when the user is not found
                	response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"User not found.\"}");
                }
               
            } catch (NumberFormatException e) {
                // Handle invalid user ID format
            	 response.setContentType("application/json");
                 response.getWriter().write("{\"error\": \"Invalid User ID format.\"}");
            }
        } else {
            // Handle the case when userId parameter is missing in the path
        	 response.setContentType("application/json");
             response.getWriter().write("{\"error\": \"userId parameter is missing in the path.\"}");
        }// TODO Auto-generated method stub
		
	}
    
	private void GetAllUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    List<Employee> employees = localUser.getAll();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        
       
        for (Employee employee : employees) {
        	

        	timingPK = new TimingPK(employee.getUserId(),null) ;
        	
        //	pkEJB = new UsercheckinoutPK(employee.getUserId(),getCurrentDate()) ;
            Timing inout = localTiming.get(timingPK) ;
			 System.out.println("Processing employee: " + employee.getUserId() + ", CheckIn: " + inout.getUserIn() + ", CheckOut: " + inout.getUserOut());

            jsonArrayBuilder.add(Json.createObjectBuilder()
                    .add("userId", employee.getUserId())
                    .add("userName", employee.getUserName())
                    .add("checkIn", inout.getUserIn() != null ? inout.getUserIn().toString() : "N/A") 
                    .add("checkOut", inout.getUserOut() != null ? inout.getUserOut().toString() : "N/A")
                    .add("templateData", Base64.getEncoder().encodeToString(employee.getTemplateData()))
                   
            );
        }
        JsonArray jsonArray = jsonArrayBuilder.build();

        
        response.setContentType("application/json");

        
        try (PrintWriter writer = response.getWriter()) {
            writer.write(jsonArray.toString());
        }
   
	
}

	  private void getDetails(HttpServletRequest request, HttpServletResponse response,int userId) throws IOException {
	    	String pathInfo = request.getPathInfo();
	          if (pathInfo != null) {
	              try {
	                  
	                  
	                  
	                	  
	          	        if (localUser.get(userId) != null ) {
	          	        	TimingPK pk = new TimingPK(userId,null);
	          	            List<Timing> detailsList = localTiming.getAll(pk);

	          	            // Convert detailsList to JSON and send the response
	          	            String jsonResponse = convertDetailsListToJson(detailsList);
	          	            response.setContentType("application/json");
	          	            response.getWriter().write(jsonResponse);
	          	        } else {
	          	            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	          	            response.getWriter().write("Invalid user ID");
	          	        }
	                 
	              } catch (NumberFormatException e) {
	                  // Handle invalid user ID format
	              	 response.setContentType("application/json");
	                   response.getWriter().write("{\"error\": \"Invalid User ID format.\"}");
	              }
	          } else {
	              // Handle the case when userId parameter is missing in the path
	          	 response.setContentType("application/json");
	               response.getWriter().write("{\"error\": \"userId parameter is missing in the path.\"}");
	          }// TODO Auto-generated method stub
			
		}

		private String convertDetailsListToJson(List<Timing> detailsList) {
	        if (detailsList == null) {
	            // Handle the case where detailsList is null (e.g., log an error)
	            return "[]"; // Return an empty JSON array or handle it based on your requirements
	        }

	        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

	        for (Timing timing : detailsList) {
	            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder()
	                    .add("userDay", timing.getId().getUserDay().toString())
	                    .add("CheckIn", timing.getUserIn() != null ? timing.getUserIn().toString() : "N/A")
	                    .add("CheckOut", timing.getUserOut() != null ? timing.getUserOut().toString() : "N/A");

	            jsonArrayBuilder.add(jsonObjectBuilder);
	        }

	        JsonArray jsonArray = jsonArrayBuilder.build();
	        return jsonArray.toString();
	    }

	    
	
	
	
	
	//@todo
	
	
	
	
	
	
	
public static Date getCurrentDate() {
        return new Date();
    }
    
	
	
	  @FunctionalInterface
	    private interface TimeSetter {
	        void setTime(Time time);
	    }
	private void updateTimeFromJson(JsonObject json, String field, TimeSetter timeSetter) {
        String timeStr = json.getString(field, "");
        if (!timeStr.isEmpty() && !timeStr.equalsIgnoreCase("null")) {
            try {
                if ("N/A".equalsIgnoreCase(timeStr)) {
                    // Handle "N/A" case, set time to null or any default value as needed
                    timeSetter.setTime(null); // or set to a default time
                } else {
                    LocalTime time = LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("HH:mm"));
                    timeSetter.setTime(Time.valueOf(time));
                    System.out.println("Time String: " + timeStr);
                }
            } catch (DateTimeParseException e) {
                e.printStackTrace();
            }
        }
    }


    private void sendUpdateResponse(HttpServletResponse response, boolean success) throws IOException {
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();

        if (success) {
            writer.write("{\"status\": \"success\"}");
        } else {
            writer.write("{\"status\": \"error\", \"message\": \"Failed to save user.\"}");
        }

        writer.flush();
    }

    private String readJsonPayload(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder jsonPayload = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonPayload.append(line);
        }
        return jsonPayload.toString();
    }

    private JsonObject parseJson(String json) {
        JsonReader jsonReader = Json.createReader(new StringReader(json));
        return jsonReader.readObject();
    }

    

	 private boolean isValidApiKey(String apiKey) {
	        return apiKey != null && apiKey.equals("^lUL%Li!YHpPdtX*mQbiIpuqDob[0*98>@g>j[aefK;MHs>:;o<V*RYqJ5ff1iDz[vQQFO(+Yg-:TfU%,qSs3qI!]=Oa8H3@(vg");
	    }
}
