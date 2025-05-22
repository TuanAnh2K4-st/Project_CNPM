package Controller;

import Cart.Cart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(name = "DeleteCartController", value = "/delcart")
public class DeleteCartController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productId = request.getParameter("productId");

        // Lấy giỏ hàng từ session
        Cart cart = (Cart) request.getSession().getAttribute("cart");

        // Gọi phương thức xóa sản phẩm từ giỏ hàng
        if (cart != null) {
            cart.removeProduct(productId);
        }

        // Chuyển hướng trở lại trang giỏ hàng
        response.sendRedirect("giohang.jsp");
    }
}

