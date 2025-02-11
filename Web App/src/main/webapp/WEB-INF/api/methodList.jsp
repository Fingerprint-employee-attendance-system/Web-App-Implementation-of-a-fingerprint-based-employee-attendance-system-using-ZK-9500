<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>API</title>
    <!-- Bootstrap CSS link -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-e3JnLE7pJwa9Z5LGz8YR1O+9fKp6N4/iTWHbRSI62Iv3R/GTVE0KJ8h6L6IS6O+d" crossorigin="anonymous">
</head>
<body>
    <div class="container">
        <h1 class="mt-5">UserService Methods</h1>
        <ul class="list-group mt-3">
            <li class="list-group-item"><a href="methodPage.jsp?method=AddUser" class="text-decoration-none">Add User</a></li>
            <li class="list-group-item"><a href="methodPage.jsp?method=UpdateUser" class="text-decoration-none">Update User</a></li>
            <li class="list-group-item"><a href="methodPage.jsp?method=DeleteUser" class="text-decoration-none">Delete User</a></li>
            <li class="list-group-item"><a href="methodPage.jsp?method=GetUser" class="text-decoration-none">Get User</a></li>
            <li class="list-group-item"><a href="methodPage.jsp?method=GetAllUsers" class="text-decoration-none">Get All Users</a></li>
        </ul>
    </div>

    <!-- Bootstrap JS and Popper.js script links -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-mQ93GR66B00ZXjt0YO5KlohRA5SY2Xof+EAkY5qF7CGFHA+2kXOzFSFz8x4qo2eD" crossorigin="anonymous"></script>
</body>
</html>
