package main;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

public class Users {
	
	public boolean AddUser(int iFid, String username , byte[] regTemp ) {
		
		 boolean result = false;
           System.out.print(iFid + "this is the id ");
	        try {
	            String endpoint = "http://localhost:8080/Web/Api?operation=AddUser";
	            URL url = new URL(endpoint);
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setRequestMethod("POST");
	            connection.setRequestProperty("Content-Type", "application/json");
		        connection.setRequestProperty("ubermenschios777", "^lUL%Li!YHpPdtX*mQbiIpuqDob[0*98>@g>j[aefK;MHs>:;o<V*RYqJ5ff1iDz[vQQFO(+Yg-:TfU%,qSs3qI!]=Oa8H3@(vg"); // Replace with your actual API key

	            connection.setDoOutput(true);
	            connection.setDoInput(true);
	            String jsonInputString = "{\"id\": " + iFid + ", \"username\": \"" + username + "\", \"templateData\": \"" + Base64.getEncoder().encodeToString(regTemp) + "\"}";

	            // Write the JSON payload to the output stream
	            try (OutputStream os = connection.getOutputStream()) {
	                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
	                os.write(input, 0, input.length);
	            }
	            int responseCode = connection.getResponseCode();
	            if (responseCode == HttpURLConnection.HTTP_OK) {
	                result = true;
	            } else {
	                System.out.println("Error: HTTP request failed with response code " + responseCode);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return result;

	}
	

    public  String getTime() {
    	  final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.now().format(TIME_FORMATTER);
    }
    
    
	public boolean UpdateUser(Employee e) {
	    boolean result = false;

	    try {
	        String endpoint = "http://localhost:8080/Web/Api?operation=UpdateUser";
	        URL url = new URL(endpoint);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Content-Type", "application/json");
	        connection.setRequestProperty("ubermenschios777", "^lUL%Li!YHpPdtX*mQbiIpuqDob[0*98>@g>j[aefK;MHs>:;o<V*RYqJ5ff1iDz[vQQFO(+Yg-:TfU%,qSs3qI!]=Oa8H3@(vg"); // Replace with your actual API key

	        connection.setDoOutput(true);
	        connection.setDoInput(true);

	        // Assuming that iFid, username, and regTemp are available
	        String jsonInputString = "{\"id\": " + e.getUserId() +
	                                  ", \"username\": \"" + e.getUserName() +
	                                  "\", \"templateData\": \"" + Base64.getEncoder().encodeToString(e.getTemplateData()) +
	                                  "\", \"checkIn\": \"" + e.getCheckIn() +
	                                  "\", \"checkOut\": \"" + e.getCheckOut() + "\"}";

	        // Write the JSON payload to the output stream
	        try (OutputStream os = connection.getOutputStream()) {
	            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
	            os.write(input, 0, input.length);
	        }

	        int responseCode = connection.getResponseCode();
	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            result = true;
	        } else {
	            System.out.println("Error: HTTP request failed with response code " + responseCode);
	        }
	    } catch (MalformedURLException me) {
	        me.printStackTrace();
	        // Handle URL-related issues
	    } catch (IOException ie) {
	        ie.printStackTrace();
	        // Handle general I/O issues
	    } catch (Exception ee) {
	        ee.printStackTrace();
	        // Handle other exceptions
	    }

	    return result;
	}



	public boolean DeleteUser(String userId) {
	    boolean deletedOrNot = false;
	    HttpURLConnection connection = null;

	    try {
	        String endpoint = "http://localhost:8080/Web/Api/" + URLEncoder.encode(userId, StandardCharsets.UTF_8)+"?operation=DeleteUser";
	        URL url = new URL(endpoint);

	        connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("DELETE");
	        connection.setRequestProperty("ubermenschios777", "^lUL%Li!YHpPdtX*mQbiIpuqDob[0*98>@g>j[aefK;MHs>:;o<V*RYqJ5ff1iDz[vQQFO(+Yg-:TfU%,qSs3qI!]=Oa8H3@(vg"); // Replace with your actual API key

	        int responseCode = connection.getResponseCode();

	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            deletedOrNot = true;
	        } else {
	            System.out.println("Error: HTTP request failed with response code " + responseCode);
	            // Handle other response codes if needed
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        // Handle the exception according to your requirements
	    } finally {
	        if (connection != null) {
	            connection.disconnect(); // Close the connection in the finally block
	        }
	    }

	    return deletedOrNot;
	}



	public Employee GetUser(String userID)  {
	    Employee employee = null; 

	    try {
            String endpoint = "http://localhost:8080/Web/Api//" + URLEncoder.encode(userID, StandardCharsets.UTF_8)+"?operation=GetUser";
	        URL url = new URL(endpoint);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("GET");
            connection.setRequestProperty("ubermenschios777", "^lUL%Li!YHpPdtX*mQbiIpuqDob[0*98>@g>j[aefK;MHs>:;o<V*RYqJ5ff1iDz[vQQFO(+Yg-:TfU%,qSs3qI!]=Oa8H3@(vg"); // Replace with your actual API key
	        int responseCode = connection.getResponseCode();

	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            try (JsonReader jsonReader = Json.createReader(connection.getInputStream())) {
	                JsonObject jsonResponse = jsonReader.readObject();
	                int userId = jsonResponse.getInt("userId");
	                String userName = jsonResponse.getString("userName");
	                String templateDataBase64 = jsonResponse.getString("templateData");
	                byte[] templateData = Base64.getDecoder().decode(templateDataBase64);
	                
	                String checkInTime = jsonResponse.getString("CheckIn");
	                String checkOutTime = jsonResponse.getString("CheckOut");
	               // String checkInString = jsonResponse.getString("CheckIn", "N/A");
	              //  String checkOutString = jsonResponse.getString("CheckOut", "N/A");
	              //  LocalTime checkInTime = "N/A".equals(checkInString) ? null : LocalTime.parse(checkInString);
	               // LocalTime checkOutTime = "N/A".equals(checkOutString) ? null : LocalTime.parse(checkOutString);
	                employee = new Employee(userId, userName, templateData, checkInTime, checkOutTime);
	            }
	        } else {
	            System.out.println("Error: HTTP request failed with response code " + responseCode);
	        }
	    } catch (IOException e) {
	        e.printStackTrace(); 
	    }

	    return employee;
	}
	public void printEmployeeList(List<Employee> employeeList) {
	    for (Employee employee : employeeList) {
	        System.out.println("User ID: " + employee.getUserId());
	        System.out.println("User Name: " + employee.getUserName());
	        System.out.println("Template Data: " + Arrays.toString(employee.getTemplateData()));

	        String checkInTime = employee.getCheckIn();
	        String checkOutTime = employee.getCheckOut();

	        System.out.println("Check-In Time: " + (checkInTime != null ? checkInTime.toString() : "N/A"));
	        System.out.println("Check-Out Time: " + (checkOutTime != null ? checkOutTime.toString() : "N/A"));

	        System.out.println("--------------");
	    }
	}


	public List<Employee> GetAllUsers() {
	    List<Employee> employeeList = new ArrayList<>();
	   // JsonReader jsonReader;
	    try {
	    
	        String servletUrl = "http://localhost:8080/Web/Api?operation=GetAllUsers";
	        URL url = new URL(servletUrl);

	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("GET");

	        // Set your API key if required
	        connection.setRequestProperty("ubermenschios777", "^lUL%Li!YHpPdtX*mQbiIpuqDob[0*98>@g>j[aefK;MHs>:;o<V*RYqJ5ff1iDz[vQQFO(+Yg-:TfU%,qSs3qI!]=Oa8H3@(vg"); 

	        int responseCode = connection.getResponseCode();

	        if (responseCode == HttpURLConnection.HTTP_OK) {
	        	JsonReader jsonReader = Json.createReader(connection.getInputStream());
	                JsonArray jsonArray = jsonReader.readArray();

	                for (JsonValue jsonValue : jsonArray) {
	                    JsonObject jsonObject = (JsonObject) jsonValue;
	                    int userId = jsonObject.getInt("userId");
	                    String userName = jsonObject.getString("userName");
	                    byte[] templateData = Base64.getDecoder().decode(jsonObject.getString("templateData"));

	                    
	                    String checkInTime = jsonObject.getString("checkIn");
	                    String checkOutTime = jsonObject.getString("checkOut");
	                    
	                    // Assuming "checkIn" and "checkOut" are formatted date strings
	                  //  String checkInString = jsonObject.getString("checkIn", "N/A");
	                  //  String checkOutString = jsonObject.getString("checkOut", "N/A");
	                 //   LocalTime checkInTime = "N/A".equals(checkInString) || checkInString == null ? null : LocalTime.parse(checkInString);
	                 //   LocalTime checkOutTime = "N/A".equals(checkOutString) || checkOutString == null ? null : LocalTime.parse(checkOutString);

	                    Employee employee = new Employee(userId, userName, templateData);
	                    
	                    if (checkInTime != null) {
	                        employee.setCheckIn(checkInTime);
	                    }
	                    if (checkOutTime != null) {
	                        employee.setCheckOut(checkOutTime);
	                    }

	                    employeeList.add(employee);
	                }
	            
	            	jsonReader.close();
	            
	        } else {
	            throw new IOException("HTTP request failed with response code: " + responseCode);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return employeeList;
	    
	}

	public List<CheckInOutDetails> getDetails(String userID) {
	    List<CheckInOutDetails> detailsList = new ArrayList<>();

	    try {
	        String endpoint = "http://localhost:8080/Web/Api//" + URLEncoder.encode(userID, StandardCharsets.UTF_8)+"?operation=getDetails";
	        URL url = new URL(endpoint);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("GET");
	        connection.setRequestProperty("ubermenschios777", "^lUL%Li!YHpPdtX*mQbiIpuqDob[0*98>@g>j[aefK;MHs>:;o<V*RYqJ5ff1iDz[vQQFO(+Yg-:TfU%,qSs3qI!]=Oa8H3@(vg");
	        int responseCode = connection.getResponseCode();

	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            try (JsonReader jsonReader = Json.createReader(connection.getInputStream())) {
	                JsonArray jsonArray = jsonReader.readArray();

	                for (JsonValue jsonValue : jsonArray) {
	                    JsonObject jsonObject = (JsonObject) jsonValue;

	                    String day = jsonObject.getString("userDay");
	                    String in = jsonObject.getString("CheckIn", "N/A");
	                    String out = jsonObject.getString("CheckOut", "N/A");

	                    detailsList.add(new CheckInOutDetails(day, in, out));
	                }
	            }
	        } else {
	            System.out.println("Error: HTTP request failed with response code " + responseCode);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return detailsList;
	}


}
