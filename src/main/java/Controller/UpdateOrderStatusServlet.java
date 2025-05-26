package Controller;

import DAO.OrderDAO;
import Model.Order;
import Model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/updateOrderStatus")
public class UpdateOrderStatusServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String orderId = request.getParameter("id");
        String newStatus = request.getParameter("status");

        OrderDAO dao = new OrderDAO();
        Order order = dao.selectById(new Order(orderId, null, null, null, null, null, null));

        if (order != null) {
            order.setStatus(newStatus);
            dao.update(order);
        }

        response.sendRedirect("quanlyhoadon.jsp"); // quay lại trang danh sách hóa đơn
    }
}
