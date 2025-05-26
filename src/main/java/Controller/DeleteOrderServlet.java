package Controller;

import DAO.OrderDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/deleteOrder")
public class DeleteOrderServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String orderId = request.getParameter("id");

        OrderDAO dao = new OrderDAO();
        dao.delete(orderId);  // gọi hàm xóa trong DAO theo ID

        response.sendRedirect("quanlyhoadon.jsp"); // quay lại trang danh sách hóa đơn
    }
}
