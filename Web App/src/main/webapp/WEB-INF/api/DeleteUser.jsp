<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Delete User Method</title>
</head>
<body>
    <h1>Delete User Method Page</h1>
    
    <form action="deleteUser" method="post">
        <label for="userId">User ID:</label>
        <input type="text" id="userId" name="userId" required>
        <br>
        <input type="submit" value="Delete User">
    </form>
    
    <br>
    <a href="methodList.jsp">Back to Method List</a>
</body>
</html>
