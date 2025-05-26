package Controller;

import Cart.Cart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(name = "AddCartController", value = "/addcart")
public class AddCartController extends HttpServlet {
    @Override
//  3.2.1. Gọi phương thức doPost() của AddCartController để xử lý request
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//  3.2.2. Lấy đối tượng HttpSession hiện tại từ request.getSession()
        HttpSession session = request.getSession();
//  3.2.3. Kiểm tra thuộc tính "cart" trong session
        Cart cart = (Cart) session.getAttribute(("cart"));
//  Nếu thuộc tính "cart" chưa tồn tại (lần đầu truy cập hoặc session mới), ta khởi tạo đối tượng Cart mới:
        if (cart == null) cart = new Cart();
        String id = request.getParameter("id");
//  3.2.4. Gọi cart.add(id) để thêm sản phẩm
        cart.add(id);
//  3.2.5. Cập nhật lại giỏ hàng trong session
        session.setAttribute("cart", cart);
//  3.2.6. Chuyển hướng người dùng về trang giohang.jsp
        response.sendRedirect("giohang.jsp");

    }
}

