<%@ page import="Model.Product" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% List<Product> data = (List<Product>) request.getAttribute("list");%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Danh mục sản phẩm</title>

    <!-- Google font -->
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,500,700" rel="stylesheet">

    <!-- Bootstrap -->
    <link type="text/css" rel="stylesheet" href="css/bootstrap.min.css"/>

    <!-- Slick -->
    <link type="text/css" rel="stylesheet" href="css/slick.css"/>
    <link type="text/css" rel="stylesheet" href="css/slick-theme.css"/>

    <!-- nouislider -->
    <link type="text/css" rel="stylesheet" href="css/nouislider.min.css"/>

    <!-- Font Awesome Icon -->
    <link rel="stylesheet" href="css/font-awesome.min.css">

    <!-- Custom stlylesheet -->
    <link type="text/css" rel="stylesheet" href="css/style.css"/>

    <jsp:useBean id="a" class="DAO.ProductDAO" scope="request"></jsp:useBean>
    <jsp:useBean id="b" class="DAO.ProducerDAO" scope="request"></jsp:useBean>
    <jsp:useBean id="c" class="DAO.ProductTypeDAO" scope="request"></jsp:useBean>
    <style>
        .error-message {
            color: #d9534f;
            background-color: #f2dede;
            border-color: #ebccd1;
            padding: 15px;
            margin-bottom: 20px;
            border: 1px solid transparent;
            border-radius: 4px;
            text-align: center;
        }

        .info-message {git
            color: #31708f;
            background-color: #d9edf7;
            border-color: #bce8f1;
            padding: 15px;
            margin-bottom: 20px;
            border: 1px solid transparent;
            border-radius: 4px;
            text-align: center;
        }
    </style>
</head>
<body>
<!-- HEADER -->
<jsp:include page="header.jsp"/>
<!-- /HEADER -->

<!-- MENU -->
<jsp:include page="menu.jsp"/>
<!-- /MENU -->

<!-- BREADCRUMB -->
<div class="section">
    <!-- container -->
    <div class="container">
        <!-- row -->
        <div class="row">
            <!-- store products -->
            <div id="content" class="row">
                <!-- Hiển thị thông báo lỗi hoặc không tìm thấy kết quả nếu có -->
                <c:if test="${not empty errorMessage}">
                    <!-- Hiển thị thông báo lỗi - Luồng thay thế: Từ khóa không hợp lệ -->
                    <div class="col-md-12">
                        <div class="error-message">${errorMessage}</div>
                    </div>
                </c:if>
                <c:if test="${not empty noResultsMessage}">
                    <!-- Hiển thị thông báo không tìm thấy - Luồng thay thế: Không tìm thấy sản phẩm -->
                    <div class="col-md-12">
                        <div class="info-message">${noResultsMessage}</div>
                    </div>
                </c:if>
                <!-- Bước 5.5: Hiển thị danh sách sản phẩm tìm thấy -->
                <% if (data.size() >= 1) {%>
                <% for (Product p : data){ %>
                <div class="col-md-4 col-xs-6">
                    <div class="product">
                        <div class="product-img">
                            <img src="<%=p.getImg()%>" alt="">
                        </div>
                        <div class="product-body">
                            <p class="product-category"><%=p.getProducer().getName()%></p>
                            <!-- Bước 5.6: Người dùng có thể chọn một sản phẩm để xem chi tiết -->
                            <h3 class="product-name"><a href="sanpham.jsp"><%=p.getName()%></a></h3>
                            <fmt:formatNumber value="<%=p.getPrice()%>" type="number"
                                              pattern="#,##0" var="formattedPrice"/>
                            <h4 class="product-price">${formattedPrice} VNĐ</h4>
                            <div class="product-rating">
                            </div>
                            <div class="product-btns">
                            </div>
                        </div>
                        <!-- Bước 5.7: Người dùng có thể thêm sản phẩm vào giỏ hàng từ danh sách kết quả -->
                        <div class="add-to-cart">
                            <form action="addcart" method="post">
                                <input type="hidden" name="id" value="<%= p.getId() %>">
                                <button type="submit" class="add-to-cart-btn"><i class="fa fa-shopping-cart"></i> Thêm vào giỏ hàng</button>
                            </form>
                        </div>
                    </div>
                </div>
                <% } %>
                <% } else if (request.getAttribute("errorMessage") == null && request.getAttribute("noResultsMessage") == null) { %>
                <!-- Hiển thị thông báo khi không có kết quả mà không phải do lỗi -->
                <div class="col-md-12">
                    <div class="info-message">
                        Rất tiếc, Phone Accessories không tìm thấy kết quả nào phù hợp với từ khóa.
                        <br>
                        <a href="index.jsp" class="btn btn-primary mt-3">Quay lại trang chủ</a>
                    </div>
                </div>
                <% } %>
            </div>
            <!-- /store products -->
        </div>
        <!-- /row -->
    </div>
    <!-- /container -->
</div>
<!-- /SECTION -->
<!-- ... phần footer ... -->
<!-- /row -->
<!-- /container -->
<!-- /SECTION -->


<!-- FOOTER -->
<jsp:include page="footer.jsp"/>
<!-- /FOOTER -->


<!-- jQuery Plugins -->
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/slick.min.js"></script>
<script src="js/nouislider.min.js"></script>
<script src="js/jquery.zoom.min.js"></script>
<script src="js/main.js"></script>

</body>
</html>
