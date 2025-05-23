package Controller;

import DAO.ProductDAO;
import Model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * SearchController - Controller xử lý chức năng tìm kiếm sản phẩm
 *
 * Use Case: Tìm kiếm sản phẩm
 * - Người dùng nhập từ khóa vào ô tìm kiếm ở trang chủ
 * - Hệ thống tìm kiếm và hiển thị kết quả
 */
@WebServlet(name = "SearchController", value = "/search")
public class SearchController extends HttpServlet {
    /**
     * SearchController - Controller xử lý chức năng tìm kiếm sản phẩm
     *
     * Use Case: Tìm kiếm sản phẩm
     * - Bước 5.1: Người dùng truy cập giao diện chính
     * - Bước 5.2: Người dùng nhập từ khóa và submit form tìm kiếm
     * - Bước 5.3-5.7: Hệ thống xử lý tìm kiếm và hiển thị kết quả
     */
    /**
     * Pattern để kiểm tra từ khóa chứa ký tự đặc biệt
     * Sử dụng trong luồng thay thế 2: Từ khóa chứa ký tự đặc biệt
     */
    private static final Pattern SPECIAL_CHARS = Pattern.compile("[!@#$%^&*(),.?\":{}|<>]");

    /**
     * Xử lý request GET từ form tìm kiếm
     *
     * Bước 5.1-5.2: Khi người dùng submit form tìm kiếm từ header.jsp
     * - Form gửi request GET đến URL "/search" với parameter "search"
     *
     * @param request  HTTP request chứa tham số tìm kiếm
     * @param response HTTP response
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Chuyển xử lý cho doPost để thống nhất logic xử lý
        doPost(request, response);
    }

    /**
     * Xử lý chính cho việc tìm kiếm sản phẩm
     *
     * Bước 5.3: Hệ thống kiểm tra dữ liệu đầu vào
     * - Kiểm tra từ khóa rỗng (Luồng thay thế 1)
     * - Kiểm tra từ khóa chứa ký tự đặc biệt (Luồng thay thế 2)
     *
     * Bước 5.4: Hệ thống thực hiện truy vấn CSDL
     * - Gọi ProductDAO.searchByName() để tìm kiếm sản phẩm
     * - Xử lý trường hợp không tìm thấy kết quả (Luồng thay thế 3)
     *
     * Bước 5.5: Hệ thống hiển thị danh sách sản phẩm
     * - Đặt kết quả vào request attribute và forward đến JSP
     *
     * Xử lý ngoại lệ (Exception): Mất kết nối hoặc lỗi truy vấn CSDL
     *
     * @param request  HTTP request chứa tham số tìm kiếm
     * @param response HTTP response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Thiết lập encoding để xử lý đúng ký tự Unicode
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        try {
            // Lấy từ khóa tìm kiếm từ request parameter
            String textSearch = request.getParameter("search");

            // Bước 5.3: Kiểm tra tính hợp lệ của từ khóa
            if (textSearch == null || textSearch.trim().isEmpty()) {
                // Luồng thay thế: Từ khóa rỗng
                request.setAttribute("errorMessage", "Vui lòng nhập từ khóa.");
                request.setAttribute("list", new ArrayList<Product>());
            }
            // Kiểm tra từ khóa chứa ký tự đặc biệt
            else if (SPECIAL_CHARS.matcher(textSearch).find()) {
                // Luồng thay thế: Từ khóa chứa ký tự đặc biệt
                request.setAttribute("errorMessage", "Từ khóa không hợp lệ. Vui lòng không sử dụng ký tự đặc biệt.");
                request.setAttribute("list", new ArrayList<Product>());
            }
            else {
                // Bước 5.4: Gọi ProductDAO.searchByName() để thực hiện tìm kiếm
                List<Product> list = ProductDAO.searchByName(textSearch);

                // Lưu danh sách kết quả vào request attribute để hiển thị
                request.setAttribute("list", list);

                // Luồng thay thế: Không tìm thấy sản phẩm
                if (list.isEmpty()) {
                    request.setAttribute("noResultsMessage",
                            "Không tìm thấy sản phẩm phù hợp.");
                }
            }
        } catch (Exception e) {
            // Bước 4.6: Xử lý ngoại lệ - Hệ thống gặp lỗi khi truy vấn dữ liệu
            e.printStackTrace();

            // Hiển thị thông báo lỗi
            request.setAttribute("errorMessage", "Lỗi hệ thống. Vui lòng thử lại sau.");

            // Đặt danh sách trống để tránh lỗi null pointer trong JSP
            request.setAttribute("list", new ArrayList<Product>());
        }

        // Bước 5.5: Chuyển tiếp đến trang hiển thị kết quả tìm kiếm
        request.getRequestDispatcher("SearchProduct.jsp").forward(request, response);
    }
}