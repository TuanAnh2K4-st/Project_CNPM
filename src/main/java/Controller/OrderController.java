package Controller;

import Cart.Cart;
import Cart.CartProduct;
import DAO.IUserDao;
import DAO.OrderDAO;
import DAO.OrderDetailsDAO;
import DAO.UserDAO;
import DAO.impl.userDaoImpl;
import service.IUserService;
import service.UserService;
import service.impl.userServiceImpl;
import Model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet(name = "OrderController", value = "/order")
public class OrderController extends HttpServlet {
    private OrderDAO orderDAO = new OrderDAO();
    private OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();

    private UserDAO userDAO = new UserDAO();

    private IUserService userService = new userServiceImpl();
    private static int countIdOrder = 0;
    private static int countIdOrderDetail = 0;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart != null) {

            Date order_date = new Date(System.currentTimeMillis() + 3600000);
            long sevenDaysInMillis = 3 * 24 * 60 * 60 * 1000;
            Date delivery_date = new Date(order_date.getTime() + sevenDaysInMillis);

            String id_order = "or_" + generateUniqueOrderId();

            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String address = request.getParameter("delivery_address");
            String phone = request.getParameter("phone_number");
            String payment_method = request.getParameter("payment");

            if (email != null && !email.isEmpty()) {
                User user = userDAO.getUserByEmail(email);
                user.setId(userDAO.getUserByEmail(email).getId());

                Order order = new Order(id_order, user, address, "", payment_method, order_date, delivery_date);
                orderDAO.insert(order);

                List<CartProduct> cartProducts = cart.getCartProducts();

                for (CartProduct cartProduct : cartProducts) {

                    String id_orderDetail = "od_" + generateUniqueOrderDetailId();

                    OrderDetails orderDetails = new OrderDetails();

                    double amount = cartProduct.getProduct().getPrice() * cartProduct.getQuantity();

                    orderDetails.setId(id_orderDetail);
                    orderDetails.setOrder(order);
                    orderDetails.setProduct(cartProduct.getProduct());
                    orderDetails.setQuantity(cartProduct.getQuantity());
                    orderDetails.setPrice(cartProduct.getProduct().getPrice());
                    orderDetails.setDiscount(0);
                    orderDetails.setAmount(amount);

                    orderDetailsDAO.insert(orderDetails);
                }
                session.removeAttribute("cart");
            }else{
                request.setAttribute("error", "Tên người dùng hoặc email hoặc số điện thoại không chính xác!");
                RequestDispatcher dispatcher = request.getRequestDispatcher("thanhtoan.jsp");
                dispatcher.forward(request, response);
            }
        }
        session.setAttribute("OrderSuccess", true);
        response.sendRedirect("thanhtoan.jsp");
    }

    private synchronized String generateUniqueOrderId() {
        countIdOrder++;
        return String.valueOf(countIdOrder);
    }

    private synchronized String generateUniqueOrderDetailId() {
        countIdOrderDetail++;
        return String.valueOf(countIdOrderDetail);
    }
}
