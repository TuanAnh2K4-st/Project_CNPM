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
//   3.3.2. DeleteCartController gọi phương thức doPost() để xử lý request.
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//  3.3.1. Form gửi request đến DeleteCartController (thường qua phương thức POST) với tham số productId của sản phẩm cần xóa.
        String productId = request.getParameter("productId");

//  3.3.3. Lấy đối tượng HttpSession hiện tại từ request.getSession().
//  3.3.4. Lấy thuộc tính "cart" trong session:
        Cart cart = (Cart) request.getSession().getAttribute("cart");

        // Gọi phương thức xóa sản phẩm từ giỏ hàng
        if (cart != null) {
//  3.3.5. Gọi cart.removeProduct(productId) để xóa sản phẩm khỏi giỏ hàng.
            cart.removeProduct(productId);
//  3.3.6. Cập nhật lại giỏ hàng trong session
            request.getSession().setAttribute("cart", cart);
        }

//  3.3.7. Chuyển hướng người dùng về trang giohang.jsp
        response.sendRedirect("giohang.jsp");
    }
}

