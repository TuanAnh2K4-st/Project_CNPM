package Controller;

import service.IUserService;
import service.impl.userServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/account/delete")
public class DeleteAccountController extends HttpServlet {
    private IUserService userService = new userServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        userService.deleteById(id);
        resp.sendRedirect("/quanlytaikhoan");
    }
}
