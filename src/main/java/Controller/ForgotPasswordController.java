package Controller;

import service.IUserService;
import service.impl.userServiceImpl;
import utils.MailUtil;
import utils.SessionUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

@WebServlet(value = "/quenmatkhau")
public class ForgotPasswordController extends HttpServlet {
    private IUserService userService = new userServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("quenmatkhau.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        if(!userService.isEmailExists(email)){
            req.setAttribute("error", "Email không tồn tại!");
            req.setAttribute("email", email);
            RequestDispatcher dispatcher = req.getRequestDispatcher("quenmatkhau.jsp");
            dispatcher.forward(req, resp);
            return;
        }
        Random random = new Random();
        String randomNumber = random.nextInt(999999) + "";
        String code = "";
        for(int i = randomNumber.length(); i < 6; i++){
            code += "0";
        }
        code += randomNumber;
        MailUtil.getInstance().sendMail("Mã code của bạn: " + code, "Mã code của bạn", email);
        SessionUtil.getInstance().putKey(req, "codes", code);
        SessionUtil.getInstance().putKey(req, "email", email);
        resp.sendRedirect("entercode");
    }
}
