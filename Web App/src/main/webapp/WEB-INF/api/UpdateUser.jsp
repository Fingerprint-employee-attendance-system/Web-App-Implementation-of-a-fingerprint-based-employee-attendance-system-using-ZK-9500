<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Update User</title>
</head>
<body>
    <h1>Update User</h1>
    <form action="updateUser" method="post">
        User ID: <input type="text" name="userId" required><br>
        New Username: <input type="text" name="userName" required><br>
        Template Data: <input type="text" name="templateData" required><br>
        Check-in: <input type="text" name="checkIn" required><br>
        Check-out: <input type="text" name="checkOut"><br>
        <input type="submit" value="Update User">
    </form>
    <br>
    <a href="methodList.jsp">Back to Method List</a>
</body>
</html>
