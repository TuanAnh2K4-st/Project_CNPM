package DAO;

import Model.*;
import db.JDBIConector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductTypeDAO implements DAOInterface<ProductType>{
    @Override
    public List<ProductType> selectAll() {
        List<ProductType> productTypes = JDBIConector.me().withHandle((handle ->
                handle.createQuery("SELECT id, name FROM product_types")
                        .mapToBean(ProductType.class).stream().collect(Collectors.toList())
        ));
        return productTypes;
    }

    @Override
    public ProductType selectById(ProductType productTypeP) {
        Optional<ProductType> productType = JDBIConector.me().withHandle((handle ->
                handle.createQuery("SELECT id, name FROM product_types WHERE id = ?")
                        .bind(0, productTypeP.getId())
                        .mapToBean(ProductType.class).stream().findFirst()
        ));
        return productType.isEmpty() ? null : productType.get();
    }

    @Override
    public int insert(ProductType productType) {
        int ketQua = 0;
        try {
            // Bước 1: tạo kết nối đến CSDL
            Connection con = JDBCUtil.getConnection();

            // Bước 2: tạo ra đối tượng statement
            String sql = "INSERT INTO product_types (id, name) "+
                    " VALUES (?,?)";

            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, productType.getId());
            st.setString(2, productType.getName());

            // Bước 3: thực thi câu lệnh SQL
            ketQua = st.executeUpdate();

            // Bước 4:
            System.out.println("Bạn đã thực thi: "+ sql);
            System.out.println("Có "+ ketQua+" dòng bị thay đổi!");

            // Bước 5:
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ketQua;
    }

    @Override
    public int delete(ProductType productType) {
        int ketQua = 0;
        try {
            // Bước 1: tạo kết nối đến CSDL
            Connection con = JDBCUtil.getConnection();

            // Bước 2: tạo ra đối tượng statement
            String sql = "DELETE from product_types "+
                    " WHERE id=?";

            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, productType.getId());

            // Bước 3: thực thi câu lệnh SQL
            System.out.println(sql);
            ketQua = st.executeUpdate();

            // Bước 4:
            System.out.println("Bạn đã thực thi: "+ sql);
            System.out.println("Có "+ ketQua+" dòng bị thay đổi!");

            // Bước 5:
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ketQua;
    }

    @Override
    public int update(ProductType productType) {
        int ketQua = 0;
        try {
            // Bước 1: tạo kết nối đến CSDL
            Connection con = JDBCUtil.getConnection();

            // Bước 2: tạo ra đối tượng statement
            String sql = "UPDATE product_types "+
                    " SET " +
                    " name=?"+
                    " WHERE id=?";

            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, productType.getName());
            st.setString(2, productType.getId());

            // Bước 3: thực thi câu lệnh SQL

            System.out.println(sql);
            ketQua = st.executeUpdate();

            // Bước 4:
            System.out.println("Bạn đã thực thi: "+ sql);
            System.out.println("Có "+ ketQua+" dòng bị thay đổi!");

            // Bước 5:
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ketQua;
    }

    public static ProductType getById(String id) {
        Optional<ProductType> productType = JDBIConector.me().withHandle((handle ->
                handle.createQuery("SELECT id, name FROM product_types WHERE id = ?")
                        .bind(0, id)
                        .map((rs, ctx) -> {
                            String nameProductType = rs.getString("name");
                            ProductType pt = new ProductType(id, nameProductType);
                            return pt;
                        }).stream().findFirst()
        ));
        return productType.isEmpty() ? null : productType.get();
    }

    public static void main(String[] args) {
        ProductTypeDAO productTypeDAO = new ProductTypeDAO();
        System.out.println(productTypeDAO.getById("Pt_1"));
    }
}
