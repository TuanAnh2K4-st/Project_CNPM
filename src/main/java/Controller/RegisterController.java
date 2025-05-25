package Controller;

import Model.User;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;

@WebServlet(value = "/register")
public class RegisterController extends HttpServlet {
    private IUserService userService = new userServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            req.setAttribute("user", new User(
                    req.getParameter("name"),
                    req.getParameter("gender"),
                    req.getParameter("address"),
                    new java.sql.Date(dateFormat.parse(req.getParameter("birthDay")).getTime()),
                    req.getParameter("phoneNumber"),
                    req.getParameter("email"),
                    req.getParameter("username"),
                    req.getParameter("password")
            ));
            req.setAttribute("confirmPassword", req.getParameter("confirmPassword"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(req.getParameter("password").toString().equals(req.getParameter("confirmPassword").toString())){
            if(userService.isUsernameExists(req.getParameter("username").toString())){
                req.setAttribute("error", "Tên người dùng đã tồn tại!");
                RequestDispatcher dispatcher = req.getRequestDispatcher("dangky.jsp");
                dispatcher.forward(req, resp);
            }else{
                dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    User user = new User(
                            req.getParameter("name"),
                            req.getParameter("gender").equals("Nam") ? "Nam" : "Nữ",
                            req.getParameter("address"),
                            new java.sql.Date(dateFormat.parse(req.getParameter("birthDay")).getTime()),
                            req.getParameter("phoneNumber"),
                            req.getParameter("email"),
                            req.getParameter("username"),
                            req.getParameter("password"),
                            "0"
                    );
                    SessionUtil.getInstance().putKey(req, "userObj", user);
                    Random random = new Random();
                    String randomNumber = random.nextInt(999999) + "";
                    String code = "";
                    for(int i = randomNumber.length(); i < 6; i++){
                        code += "0";
                    }
                    code += randomNumber;
                    MailUtil.getInstance().sendMail("Mã code của bạn: " + code, "Mã code của bạn", user.getEmail());
                    SessionUtil.getInstance().putKey(req, "codes", code);
                    resp.sendRedirect("entercode");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }else{
            req.setAttribute("error", "Mật khẩu và nhập lại mật khẩu không giống nhau");
            RequestDispatcher dispatcher = req.getRequestDispatcher("dangky.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
