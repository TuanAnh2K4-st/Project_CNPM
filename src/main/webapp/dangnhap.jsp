<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập</title>
    <link rel="stylesheet" href="css/register.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"/>
    <link type="text/css" rel="stylesheet" href="css/bootstrap.min.css"/>
</head>
<body>
<div class="wrapper">
    <h1>Đăng nhập</h1>
    <c:if test="${error != null}">
        <p class="alert alert-danger">${error}</p>
    </c:if>
    <form action="login" method="post">
        <input type="text" placeholder="Tên đăng nhập" name="username" required value="${username}">
        <input type="password" placeholder="Mật khẩu" name="password" required value="${password}">
        <div class="recover">
            <a href="quenmatkhau">Quên mật khẩu?</a>
        </div>
        <button style="border: none"> Đăng nhập</button>
    </form>
    <div class="member">
        Chưa có tài khoản ? <a href="dangky.jsp">
        Đăng ký ngay
    </a>
    </div>
</div>

<script>
    function submitForm() {
        var registrationForm = document.getElementById('registrationForm');
        if (registrationForm.checkValidity()) {
            var overlay = document.getElementById('overlay');
            overlay.style.display = 'flex';
        } else {
            alert("Vui lòng điền đầy đủ thông tin và đồng ý với các điều khoản.");
        }
    }
</script>
<div class="overlay" id="overlay">
    <div class="success-message">
        <p> Đăng nhập thành công!</p>
        <br><a href="index.jsp">Tiếp tục mua hàng</a>
    </div>
</div>

<c:if test="${success != null}">
    <script>
        alert("Đăng nhập thành công");
        document.location.href = "index.jsp";
    </script>
</c:if>
</body>
</html>