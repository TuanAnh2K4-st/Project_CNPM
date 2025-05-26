package Controller;

import service.IUserService;
import service.impl.userServiceImpl;
import utils.SessionUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginController", value = "/login")
public class LoginController extends HttpServlet {
    private IUserService userService = new userServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("dangnhap.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("username",req.getParameter("username"));
        req.setAttribute("password",req.getParameter("password"));
        if(userService.login(req.getParameter("username"), req.getParameter("password"))){
            req.setAttribute("success", "Đăng nhập thành công!");
            req.setAttribute("username","");
            req.setAttribute("password","");
            SessionUtil.getInstance().putKey(req, "user", userService.getIdByUsername(req.getParameter("username")));
            if(userService.getById(SessionUtil.getInstance().getKey(req, "user").toString()).getRole_idStr().equals("1")){
                resp.sendRedirect("admin.jsp");
            }else{
                RequestDispatcher dispatcher = req.getRequestDispatcher("dangnhap.jsp");
                dispatcher.forward(req, resp);
            }
        }else{
            req.setAttribute("error", "Tên người dùng hoặc mật khẩu không chính xác!");
            RequestDispatcher dispatcher = req.getRequestDispatcher("dangnhap.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
