<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html  lang="en" >

<head>
     <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Fingerprint Attendance System">
    <meta name="keywords" content="Fingerprint Attendance System,Fingerprint  System">
    <meta name="author" content="Bara and Achouri">

    <!-- Favicon -->
    <link rel="icon" href="favicon.ico" type="resources/png/favicon.ico">
    <link rel="apple-touch-icon" href="resources/png/favicon.ico">

    <!-- SEO Tags -->
    <title>Fingerprint Attendance System</title>
    <meta name="robots" content="index, follow">

   
    
    
    
<script  src="resources/js/jquery-3.6.4.min.js"></script>
<script  src="resources/js/chart.js"></script>
<script  src="resources/js/qrcode.min.js"></script>

<link rel="stylesheet" type="text/css" href="resources/css/styles.css">
<link rel="stylesheet" type="text/css" href="resources/css/btn.css">
<link rel="stylesheet" type="text/css" href="resources/css/admin.css">











 


 <script>

 function updateChart() {
	    // Get servlet attributes for comparison
	   var userCount = <%= request.getAttribute("UsersCount") %>;
            var thisWeek = <%= request.getAttribute("ThisWeek") %>;
            var lastWeek = <%= request.getAttribute("LastWeek") %>;
            var thisMonth = <%= request.getAttribute("ThisMonth") %>;
            var lastMonth = <%= request.getAttribute("LastMonth") %>;
            var today = <%= request.getAttribute("Today") %>;
            var yesterday = <%= request.getAttribute("Yesterday") %>;

	    var selectedValue = document.getElementById("timePeriod").value;

	    // Hide all divs
	    var divs = document.getElementsByClassName("time-period-div");
	    for (var i = 0; i < divs.length; i++) {
	        divs[i].style.display = "none";
	    }

	    // Show the selected div
	    document.getElementById(selectedValue + "Div").style.display = "block";

	    // Check the selected value and call the appropriate chart creation function
	    if (selectedValue === "week") {
	    	createComparisonChart("weeklyChart", ['Present', 'Last Week'], [thisWeek, lastWeek]);
	    } else if (selectedValue === "month") {
	    	createComparisonChart("monthlyChart", ['Present', 'Last Month'], [thisMonth, lastMonth]);
	    		    } else if (selectedValue === "today") {
	    	createComparisonChart("todayChart", ['Present', 'Yesterday'], [today, yesterday]);
	    }
	}

 function createComparisonChart(canvasId, labels, data) {
	    var ctx = document.getElementById(canvasId).getContext("2d");

	    var chart = new Chart(ctx, {
	        type: 'bar',
	        data: {
	            labels: labels,
	            datasets: [{
	                label: 'Number of Workers',
	                data: data,
	                backgroundColor: [
	                    'rgba(75, 192, 192, 0.2)',
	                    'rgba(255, 99, 132, 0.2)',
	                ],
	                borderColor: [
	                    'rgba(75, 192, 192, 1)',
	                    'rgba(255, 99, 132, 1)',
	                ],
	                borderWidth: 1
	            }]
	        },
	        options: {
	            scales: {
	                y: {
	                    beginAtZero: true
	                }
	            }
	        }
	    });
	}


	
	 
 
	 
 function getArrowHTML(firstNumber, secondNumber) {
     // Conditionally set the arrow class based on the comparison of two numbers
     if (firstNumber > secondNumber) {
         return '<i class="arrow up"></i>';
     } else if (firstNumber < secondNumber) {
         return '<i class="arrow down"></i>';
     } else {
         // If numbers are equal, return an empty string
         return '';
     }
 }

 
 
 
 function showContent() {
	    // Get the current URL
	    var currentUrl = window.location.href;

	    // Get the action parameter from the URL
	    var actionParam = getParameterByName('action', currentUrl);

	    // Hide all content divs
	    var contentDivs = document.getElementsByClassName('content-div');
	    for (var i = 0; i < contentDivs.length; i++) {
	        contentDivs[i].style.display = 'none';
	    }

	    // Show the selected content div based on the action parameter
	    if (actionParam === 'GetEmployeeLists') {
	        var dashboardDiv = document.getElementById('dashboardDiv');
	        if (dashboardDiv) {
	            dashboardDiv.style.display = 'block';
	        }
	    } else if (actionParam === 'GetAdminsLists') {
	        var adminsDiv = document.getElementById('adminsDiv');
	        if (adminsDiv) {
	            adminsDiv.style.display = 'block';
	        }
	    } else if (actionParam === 'GetStatistics') {
	        var statisticsDiv = document.getElementById('statisticsDiv');
	        if (statisticsDiv) {
	            statisticsDiv.style.display = 'block';
	        }
	    }
	}

	// Function to get a parameter value from the URL
	function getParameterByName(name, url) {
	    name = name.replace(/[\[\]]/g, "\\$&");
	    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
	        results = regex.exec(url);
	    if (!results) return null;
	    if (!results[2]) return '';
	    return decodeURIComponent(results[2].replace(/\+/g, " "));
	}

	// Call the showContent function when the page is loaded
	document.addEventListener("DOMContentLoaded", showContent);




	 

  function submitExitForm() {
        document.getElementById("exitForm").submit();
    }


  $(document).ready(function () {
	    // Attach click event to user list table rows
	    $('#userListTable tbody').on('click', 'tr', function () {

	    	// Prevent the default behavior (opening a new page)
	        event.preventDefault();
	        // Get the user ID from the clicked row
	        var userId = $(this).find('td:first').text();

	        // Redirect to the page with the specified user ID
	        window.location.href = 'DashboardServlet?action=GetEmployeeLists&userId=' + userId;
	        showContent('dashboardDiv');
	        // Make an AJAX request to fetch additional details
	        $.ajax({
	            url: 'DashboardServlet',
	            method: 'GET',
	            data: { userId: userId, action: 'GetEmployeeLists' }, // Specify the action parameter
	            success: function (data) {
	                // Update the details table with the response data
	                $('#detailsTable tbody').html(data);
	            },
	            error: function () {
	                alert('Error fetching details.');
	            }
	        });
	    });
	});


 

       
   
       
    </script>


<script>
// Function to generate and display the QR code
function generateQRCode() {
  var webIp = '<%= session.getAttribute("webIp") %>';
  var qrCode = new QRCode(document.getElementById("qrcode"), {
    text: webIp,
    width: 200,
    height: 200,
  });
}


</script>




 
    <title>Dashboard</title>
  
    
   
  


</head>


<body>
    <div class="app">
	
	
	


	
	<header class="app-header">
            <div class="header-container">
                <div class="header-item">
                    <h2>Today: ${Today}  </h2>
                    <p>Yesterday: ${Yesterday} </p>
                    <p>${todayArrow}</p>
                    
                </div>
                <div class="header-item">
                    <h2>This Week: ${ThisWeek}</h2>
                    <p>Last Week: ${LastWeek}</p>
                    <p>${weekArrow}</p>
                </div>
                <div class="header-item">
                    <h2>This Month : ${ThisMonth}</h2>
                    <p>Last Month: ${LastMonth}</p>
                    <p>${monthArrow}</p>
                </div>
                <div class="header-item">
                    <h2>${UsersCount}</h2>
                    <p>Users Count</p>
                </div>
            </div>
        </header>
	
	
	
	
	
	
	<div class="app-body">
	
	
	
	
	
	
	
	
	
		<div class="app-body-navigation">
		
		<!-- hamburger -->
  <input type="checkbox" id="navi-toggle" class="checkboxhamb" />
  <label for="navi-toggle" class="buttonhamb">
    <span class="iconhamb">&nbsp;</span>
  </label>
  <div class="backgroundhamb">&nbsp;</div>
  
  
  
  <!-- nav -->
  <nav class="navigation">
    <ul class="list">
      <li class="item">  <a class="btn" class="link" href="DashboardServlet?action=GetEmployeeLists" onclick="showContent('dashboardDiv')">
   
        <i class="Dashboard"></i>
        <span>Dashboard </span>
      
    </a> </li>
      <li class="item"> <a class="btn" href="DashboardServlet?action=GetAdminsLists" onclick="showContent('adminsDiv')">
        <i class="Admins"></i>
        <span>Admins</span>
    </a> </li>
      <li class="item"> <a class="btn"  href="DashboardServlet?action=GetStatistics" onclick="showContent('statisticsDiv')">
        <i class="statistics"></i>
        <span>Statistics</span>
    </a> </li>
    
    <li class="item"> <a class="btn"  href="#" onclick="generateQRCode()">
    <i class="Qr"></i>
    <span>Qr</span>
</a> </li>



      <li class="item"> <a class="btn"  href="#" onclick="submitExitForm()">
    <i class="Exit"></i>
    <span>Exit</span>
</a> </li>
      
    </ul>
    <!-- Add a hidden form for submitting the exit action -->
<form id="exitForm" action="DashboardServlet" method="post">
    <input type="hidden" name="action" value="exit"/>
</form>
<div id="qrcode"></div>

  </nav>
		

		
			
		
		
		<div class="app-body-main-content">
			<section class="service-section">
				<h2>Data</h2>
				
				
				
				<div  id="dashboardDiv" class="content-div">
				
				
	<form id="invisibleForm" action="DashboardServlet" method="post" style="display: none;">
    <input type="hidden" name="action" value="GetEmployeeLists">
    </form>
    
   <table id="userListTable" >
    <thead>
        <tr>
            <th>User ID</th>
             <th>User Name</th>
             <th>Check-In</th>
             <th>Check-Out</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="user" items="${userList}">
            <tr>
                <td class="btn">${user.userId} </td>
                
                <td class="btn">${user.userName}</td>
                <td class="btn">${user.checkIn}</td>
                <td class="btn">${user.checkOut}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>	




<h2>User Details</h2>




<table id="detailsTable">
    <thead>
        <tr>
            <th>Day</th>
            <th>Time In</th>
            <th>Time Out</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="detail" items="${detailsList}">
            <tr>
                <td>${detail.id.userDay}</td>
                <td>${detail.userIn != null ? detail.userIn : 'N/A'}</td>
                <td>${detail.userOut != null ? detail.userOut : 'N/A'}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>			
			
			
			
				</div>
		
		<div  id="adminsDiv" class="content-div">
		
		<form id="invisibleForm" action="DashboardServlet" method="post" style="display: none;">
    <input type="hidden" name="action" value="GetAdminsLists">
    </form>
    
    <!-- Admins content goes here -->
    <h2>Admins Content</h2>
    
    
    <table id="adminsTable">
    <thead>
        <tr>
            <th>Admin ID</th>
            <th>Admin Name</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="admin" items="${adminList}">
            <tr>
                <td class="btn">${admin.adminId}</td>
                <td class="btn">${admin.adminName}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>	

<br><br><br>


 <!-- Login div starts here -->
        <div class="admin">
            <h2>Add Admin:</h2>
             <form action="DashboardServlet" method="post">
    <input type="hidden" name="action" value="addAdmin">
            
            <div class="adminBx">
    <input type="text" placeholder="admin name" name="adminUsername" required>
         
            </div>
            <br>
            <br>
            <!-- Input container for password -->
            <div class="adminBx">
        <input type="password" placeholder="password" name="adminPassword" required>
            </div>
            <br>
            <br>
            <!-- Input container for login button -->
            <div  class="adminBx">
               <input class="btn"  type="submit" value="Add Admin">
                
            </div>
            </form>
        
    
    
    
    <br><br><br> <br>
            
    <!-- Add a button to delete admin by ID -->
<form action="DashboardServlet" method="post">
    <input type="hidden" name="action" value="deleteAdmin"/>
    
    <h2>Delete Admin:</h2>
    <div  class="adminBx">
    <input type="text" placeholder="Id" id="adminIdToDelete" name="adminIdToDelete" required/>
    </div>
     <br>
     <br>
    <div  class="adminBx">
    <input class="btn"  type="submit" value="Delete Admin" >
    </div>
     </div>
</form>
</div>
        <!-- Login div ends here -->

<div id="statisticsDiv" class="content-div">
    <h2>Statistics Content</h2>
    <div>
        <label for="timePeriod">Select Time Period:</label>
        <select id="timePeriod" onchange="updateChart()">
            <option value="week">This Week</option>
            <option value="month">This Month</option>
            <option value="today">Today</option>
        </select>
        
        
        <div  class="chartDiv">
        <div id="todayDiv" class="time-period-div" style="display: none;">
           <canvas id="todayChart" width="400" height="200"></canvas>
        </div>
        <div id="weekDiv" class="time-period-div">
            <canvas id="weeklyChart" width="400" height="200"></canvas>
        </div>
        <div id="monthDiv" class="time-period-div" style="display: none;">
            <canvas id="monthlyChart" width="400" height="200"></canvas>
        </div>
        </div>
    </div>

    
</div>
			</section>
			
		</div>
		
		
		
		<div class="app-body-sidebar">
		<div class="ring">
        <i style="--clr:#006633;"></i>
        <i style="--clr:#FFFFFF;"></i>
        <i style="--clr:#D21034;"></i>
		 
        <!-- Login div starts here -->
				<div class="app-header-logo">
			<div class="logo">
				<span class="logo-icon">
					<img src="resources/png/fingerprint-png.png">
				</span>
				<h1 class="logo-title">
					<span>Fingerprint</span>
					<br>
					<span>Attendance</span>
					<br>
					<span>System</span>
				</h1>
			
		</div>
		<button class="user-profile">
				<span>Admin: ${loggedInAdmin}</span>
				<span>
					<img src="resources/png/adminImg.png">
				</span>
			</button>
			</div>
		</div>
		</div>
	</div>
	
</div>


<footer class="footer">
				
				<div>
					<br>
					All Rights Reserved 2024
				</div>
			</footer>

<script src="moz-extension://2f53bd12-6305-4206-bafe-f5107b2e9412/js/app.js" type="text/javascript">
</script>

</body>
</html>
