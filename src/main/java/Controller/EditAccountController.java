package Controller;

import Model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import service.IUserService;
import service.impl.userServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(value = "/account/edit")
public class EditAccountController extends HttpServlet {
    private IUserService userService = new userServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(resp.getOutputStream(), userService.getById(req.getParameter("id")).getAddress());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            User user = new User(
                    req.getParameter("name"),
                    req.getParameter("gender"),
                    req.getParameter("address"),
                    new java.sql.Date(dateFormat.parse(req.getParameter("date")).getTime()),
                    req.getParameter("phone"),
                    req.getParameter("email"),
                    req.getParameter("user"),
                    req.getParameter("password")
            );
            user.setId(req.getParameter("id"));
            userService.update(user);
            resp.sendRedirect("/quanlytaikhoan");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
