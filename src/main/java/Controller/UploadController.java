package Controller;


import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@WebServlet(name = "UploadController", value = "/upload")

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 100
)
public class UploadController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("up");
        String fileName = filePart.getSubmittedFileName();
        String root = getServletContext().getRealPath("/data/");
        File check = new File(root);
        if(!(check.exists())){
            check.mkdirs();
        }
        if(filePart!=null){
            filePart.write(root + fileName);
        }
        response.getWriter().print("Hình ảnh đã tải lên thành công");
    }
}
