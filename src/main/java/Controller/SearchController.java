package Controller;

import DAO.ProductDAO;
import Model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(name = "SearchController", value = "/search")
public class SearchController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String textSearch = request.getParameter("search");
        List<Product> list = ProductDAO.searchByName(textSearch);
        request.setAttribute("list", list);
        request.getRequestDispatcher("SearchProduct.jsp").forward(request, response);
    }
}

