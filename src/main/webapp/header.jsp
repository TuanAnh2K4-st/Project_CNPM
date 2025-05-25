<%--
  Created by IntelliJ IDEA.
  User: hadun
  Date: 11/12/2023
  Time: 10:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="Cart.Cart" %>
<%@ page import="utils.SessionUtil" %>
<%@ page import="service.impl.userServiceImpl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Cart cart = (Cart) session.getAttribute("cart");
    if(cart == null) cart = new Cart();
%>
<html>
<head>
    <title>Header</title>
    <link rel="icon" type="image/png" href="./img/logo.png"/>
    <style>
        .modern-header {
            background: white;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        .top-bar {
            background: #f8f9fa;
            padding: 8px 0;
            font-size: 14px;
            border-bottom: 1px solid #eee;
        }

        .top-bar a {
            color: #666;
            text-decoration: none;
            transition: color 0.3s ease;
        }

        .top-bar a:hover {
            color: #4CAF50;
        }

        .main-header {
            padding: 20px 0;
        }

        .logo img {
            height: 50px;
            transition: transform 0.3s ease;
        }

        .logo img:hover {
            transform: scale(1.05);
        }

        .search-container {
            position: relative;
            max-width: 600px;
            margin: 0 auto;
        }

        .search-input {
            width: 100%;
            padding: 12px 20px;
            border: 2px solid #eee;
            border-radius: 30px;
            font-size: 15px;
            transition: all 0.3s ease;
        }

        .search-input:focus {
            border-color: #4CAF50;
            box-shadow: 0 0 0 2px rgba(76, 175, 80, 0.1);
            outline: none;
        }

        .search-btn {
            position: absolute;
            right: 5px;
            top: 5px;
            background: #4CAF50;
            color: white;
            border: none;
            padding: 8px 25px;
            border-radius: 25px;
            cursor: pointer;
            transition: background 0.3s ease;
        }

        .search-btn:hover {
            background: #45a049;
        }

        .header-actions {
            display: flex;
            align-items: center;
            gap: 20px;
        }

        .cart-btn {
            position: relative;
            display: flex;
            align-items: center;
            gap: 8px;
            color: #333;
            text-decoration: none;
            padding: 8px 15px;
            border-radius: 20px;
            transition: all 0.3s ease;
        }

        .cart-btn:hover {
            background: #f0f0f0;
        }

        .cart-count {
            position: absolute;
            top: -8px;
            right: -8px;
            background: #4CAF50;
            color: white;
            font-size: 12px;
            padding: 2px 6px;
            border-radius: 10px;
            min-width: 20px;
            text-align: center;
        }

        .user-menu {
            position: relative;
            padding: 8px 15px;
            cursor: pointer;
            border-radius: 20px;
            transition: all 0.3s ease;
        }

        .user-menu:hover {
            background: #f0f0f0;
        }

        @media (max-width: 768px) {
            .top-bar {
                display: none;
            }

            .search-container {
                margin: 20px 0;
            }

            .header-actions {
                justify-content: center;
            }
        }
    </style>
</head>
<body>
<!-- HEADER -->
<header class="modern-header">
    <!-- Top Bar -->
    <div class="top-bar">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-md-6">
                    <div class="d-flex gap-4">
                        <a href="tel:0973206403"><i class="fa fa-phone"></i> 0372693241</a>
                        <a href="mailto:hadung6765@gmail.com"><i class="fa fa-envelope-o"></i> anh171104@gmail.com</a>
                        <a href="#"><i class="fa fa-map-marker"></i> Quận Thủ Đức - Tp.Hồ Chí Minh</a>
                    </div>
                </div>
                <div class="col-md-6 text-end">
                    <c:if test="${sessionScope.user == null}">
                        <a href="dangky.jsp" class="me-3"><i class="fa fa-user-plus"></i> Đăng ký</a>
                        <a href="dangnhap.jsp"><i class="fa fa-sign-in"></i> Đăng nhập</a>
                    </c:if>
                    <c:if test="${sessionScope.user != null}">
                        <span class="me-3">
                            <i class="fa fa-user"></i> 
                            <%= new userServiceImpl().getById(SessionUtil.getInstance().getKey((HttpServletRequest) request, "user").toString()).getName() %>
                        </span>
                        <a href="logout"><i class="fa fa-sign-out"></i> Đăng xuất</a>
                    </c:if>
                </div>
            </div>
        </div>
    </div>

    <!-- Main Header -->
    <div class="main-header">
        <div class="container">
            <div class="row align-items-center">
                <!-- Logo -->
                <div class="col-md-2">
                    <a href="index.jsp" class="logo">
                        <img src="img/logoShop.png" alt="Logo" class="img-fluid">
                    </a>
                </div>

                <!-- Search -->
                <div class="col-md-7">
                    <div class="search-container">
                        <form action="search" method="post">
                            <input type="text" class="search-input" name="search" placeholder="Bạn đang tìm gì...">
                            <button type="submit" class="search-btn" name="btnGo">
                                <i class="fa fa-search"></i> Tìm kiếm
                            </button>
                        </form>
                    </div>
                </div>

                <!-- Actions -->
                <div class="col-md-3">
                    <div class="header-actions">
                        <a href="giohang.jsp" class="cart-btn">
                            <i class="fa fa-shopping-cart fa-lg"></i>
                            <span>Giỏ hàng</span>
                            <span class="cart-count"><%=cart.getTotal()%></span>
                        </a>

                        <div class="user-menu d-md-none">
                            <i class="fa fa-bars"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>
<!-- /HEADER -->
</body>
</html>
