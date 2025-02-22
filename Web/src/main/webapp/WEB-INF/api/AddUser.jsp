<!DOCTYPE html>
<html>
<head>
    <title>Add User</title>
</head>
<body>
    <h1>Add User</h1>
    <form action="addUser" method="post">
        <label for="userName">User Name:</label>
        <input type="text" id="userName" name="userName" required><br><br>

        <label for="templateData">Template Data (as Base64):</label>
        <input type="text" id="templateData" name="templateData" required><br><br>

       

        <input type="submit" value="Add User">
    </form>
</body>
</html>
