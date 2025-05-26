<%@ page import="Model.Order" %>
<%@ page import="DAO.OrderDAO" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quản lý hóa đơn</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4">Danh sách hóa đơn</h2>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>Mã HĐ</th>
            <th>Khách hàng</th>
            <th>Địa chỉ giao hàng</th>
            <th>Ngày đặt</th>
            <th>Ngày giao</th>
            <th>Phương thức TT</th>
            <th>Trạng thái đơn</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <%
            OrderDAO orderDAO = new OrderDAO();
            List<Order> orders = orderDAO.selectAll();
            for (Order o : orders) {
        %>
        <tr>
            <td><%=o.getId()%></td>
            <td><%=o.getUser().getId()%></td>
            <td><%=o.getAddress()%></td>
            <td><%=o.getOrderDate()%></td>
            <td><%=o.getDeliveryDate()%></td>
            <td><%=o.getPayMent()%></td>
            <td><%=o.getStatus()%></td>
            <td>
                <form action="updateOrderStatus" method="post" style="display:inline-block">
                    <input type="hidden" name="id" value="<%=o.getId()%>">
                    <select name="status" class="form-control form-control-sm d-inline" style="width: auto; display:inline-block">
                        <option value="Pending" <%=o.getStatus().equals("Pending") ? "selected" : ""%>>Pending</option>
                        <option value="Processing" <%=o.getStatus().equals("Processing") ? "selected" : ""%>>Processing</option>
                        <option value="Shipped" <%=o.getStatus().equals("Shipped") ? "selected" : ""%>>Shipped</option>
                        <option value="Delivered" <%=o.getStatus().equals("Delivered") ? "selected" : ""%>>Delivered</option>
                        <option value="Cancelled" <%=o.getStatus().equals("Cancelled") ? "selected" : ""%>>Cancelled</option>
                    </select>
                    <button type="submit" class="btn btn-primary btn-sm">Cập nhật</button>
                </form>
                <form action="deleteOrder" method="post" style="display:inline-block" onsubmit="return confirm('Bạn có chắc chắn muốn xoá hoá đơn này?');">
                    <input type="hidden" name="id" value="<%=o.getId()%>">
                    <button type="submit" class="btn btn-danger btn-sm">Xoá</button>
                </form>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>