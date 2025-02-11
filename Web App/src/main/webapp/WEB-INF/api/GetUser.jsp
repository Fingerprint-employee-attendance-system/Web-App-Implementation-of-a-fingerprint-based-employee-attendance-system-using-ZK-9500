<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Get User</title>
</head>
<body>
    <h1>Get User</h1>
    
    <form action="GetUser" method="post">
        Enter User ID: <input type="text" name="userId" required>
        <input type="submit" value="Get User">
    </form>

    <div>
        <h2>User Information:</h2>
        <p>ID: ${user.userId}</p>
        <p>Name: ${user.userName}</p>
        <!-- Add more user attributes as needed -->
    </div>
</body>
</html>
