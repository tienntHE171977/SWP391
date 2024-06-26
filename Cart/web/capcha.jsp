<%-- 
    Document   : capcha
    Created on : Jun 10, 2024, 12:26:43 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>reCAPTCHA Demo</title>
    <script src="https://www.google.com/recaptcha/api.js" async defer></script>
</head>
<body>
    <form action="verify" method="post">
        <div class="g-recaptcha" data-sitekey="6LenyvQpAAAAACJB8ifK6mVWAeeUGYRzkyCfKWOl"></div>
        <br/>
        <input type="submit" value="Submit">
    </form>
</body>
</html>

