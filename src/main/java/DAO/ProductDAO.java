package DAO;

import Model.*;
import db.JDBIConector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProductDAO implements DAOInterface<Product> {
    @Override
    public List<Product> selectAll() {
        List<Product> products = JDBIConector.me().withHandle((handle -> {
            List<Product> list = new ArrayList<>();
            handle.createQuery("SELECT id, name, price, product_type_id, quantity, producer_id, image FROM products")
                    .map((rs, ctx) -> {
                        String id = rs.getString("id");
                        String nameProduct = rs.getString("name");
                        double price = rs.getDouble("price");
                        String productType_id = rs.getString("product_type_id");
                        int quantity = rs.getInt("quantity");
                        String producer_id = rs.getString("producer_id");
                        String img = rs.getString("image");
                        Producer producer = new ProducerDAO().selectById(new Producer(producer_id, null));
                        ProductType productType = new ProductTypeDAO().selectById(new ProductType(productType_id, null));

                        Product product = new Product(id, nameProduct, price, productType, quantity, producer, img);
                        list.add(product);
                        return null;
                    }).list();
            return list;
        }));
        return products;
    }

    public static List<Product> getAll() {
        List<Product> products = JDBIConector.me().withHandle((handle -> {
            List<Product> list = new ArrayList<>();
            handle.createQuery("SELECT id, name, price, product_type_id, quantity, producer_id, image FROM products")
                    .map((rs, ctx) -> {
                        String id = rs.getString("id");
                        String nameProduct = rs.getString("name");
                        double price = rs.getDouble("price");
                        String productType_id = rs.getString("product_type_id");
                        int quantity = rs.getInt("quantity");
                        String producer_id = rs.getString("producer_id");
                        String img = rs.getString("image");
                        Producer producer = new ProducerDAO().selectById(new Producer(producer_id, null));
                        ProductType productType = new ProductTypeDAO().selectById(new ProductType(productType_id, null));

                        Product product = new Product(id, nameProduct, price, productType, quantity, producer, img);
                        list.add(product);
                        return null;
                    }).list();
            return list;
        }));
        return products;
    }
    public static Product getById(String id) {
        Optional<Product> product = JDBIConector.me().withHandle((handle ->
                handle.createQuery("SELECT id, name, price, product_type_id, quantity," +
                                " producer_id, image FROM products WHERE id = ?")
                        .bind(0, id)
                        .map((rs, ctx) -> {
                            String nameProduct = rs.getString("name");
                            double price = rs.getDouble("price");
                            String productType_id = rs.getString("product_type_id");
                            int quantity = rs.getInt("quantity");
                            String producer_id = rs.getString("producer_id");
                            String img = rs.getString("image");
                            Producer producer = new ProducerDAO().selectById(new Producer(producer_id, null));
                            ProductType productType = new ProductTypeDAO().selectById(new ProductType(productType_id, null));

                            Product p = new Product(id, nameProduct, price, productType, quantity, producer, img);
                            return p;
                        }).stream().findFirst()
        ));
        return product.isEmpty() ? null : product.get();
    }


    @Override
    public Product selectById(Product productP) {
        Optional<Product> product = JDBIConector.me().withHandle((handle ->
                handle.createQuery("SELECT id, name, price, product_type_id, quantity," +
                                " producer_id, image FROM products WHERE id = ?")
                        .bind(0, productP.getId())
                        .map((rs, ctx) -> {
                            String id = rs.getString("id");
                            String nameProduct = rs.getString("name");
                            double price = rs.getDouble("price");
                            String productType_id = rs.getString("product_type_id");
                            int quantity = rs.getInt("quantity");
                            String producer_id = rs.getString("producer_id");
                            String img = rs.getString("image");
                            Producer producer = new ProducerDAO().selectById(new Producer(producer_id, null));
                            ProductType productType = new ProductTypeDAO().selectById(new ProductType(productType_id, null));

                            Product p = new Product(id, nameProduct, price, productType, quantity, producer, img);
                            return p;
                        }).stream().findFirst()
        ));
        return product.isEmpty() ? null : product.get();
    }

    public static List<Product> searchByName(String text) {
        List<Product> products = JDBIConector.me().withHandle(handle -> {
            List<Product> list = new ArrayList<>();
            String[] keywords = text.split("\\s+"); // Tách các từ trong chuỗi

            // Xây dựng câu truy vấn SQL với biểu thức chính quy
            StringBuilder queryBuilder = new StringBuilder("SELECT id, name, price, product_type_id, quantity, producer_id, image FROM products WHERE");
            for (int i = 0; i < keywords.length; i++) {
                if (i > 0) {
                    queryBuilder.append(" AND");
                }
                queryBuilder.append(" name LIKE :keyword").append(i);
            }

            handle.createQuery(queryBuilder.toString())
                    .bindMap(IntStream.range(0, keywords.length)
                            .boxed()
                            .collect(Collectors.toMap(i -> "keyword" + i, i -> "%" + keywords[i] + "%")))
                    .map((rs, ctx) -> {
                        String id = rs.getString("id");
                        String nameProduct = rs.getString("name");
                        double price = rs.getDouble("price");
                        String productType_id = rs.getString("product_type_id");
                        int quantity = rs.getInt("quantity");
                        String producer_id = rs.getString("producer_id");
                        String img = rs.getString("image");
                        Producer producer = new ProducerDAO().selectById(new Producer(producer_id, null));
                        ProductType productType = new ProductTypeDAO().selectById(new ProductType(productType_id, null));

                        Product product = new Product(id, nameProduct, price, productType, quantity, producer, img);
                        list.add(product);
                        return null;
                    })
                    .list();
            return list;
        });

        return products;
    }
    @Override
    public int insert(Product product) {
        int ketQua = 0;
        try {
            // Bước 1: tạo kết nối đến CSDL
            Connection con = JDBCUtil.getConnection();

            // Bước 2: tạo ra đối tượng statement
            String sql = "INSERT INTO products (id, name, price, product_type_id, quantity, producer_id, image) " +
                    " VALUES (?,?,?,?,?,?,?)";

            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, product.getId());
            st.setString(2, product.getName());
            st.setDouble(3, product.getPrice());
            st.setString(4, product.getProductType().getId());
            st.setInt(5, product.getQuantity());
            st.setString(6, product.getProducer().getId());
            st.setString(7, product.getImg());

            // Bước 3: thực thi câu lệnh SQL
            ketQua = st.executeUpdate();

            // Bước 4:
            System.out.println("Bạn đã thực thi: " + sql);
            System.out.println("Có " + ketQua + " dòng bị thay đổi!");

            // Bước 5:
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ketQua;
    }

    @Override
    public int delete(Product product) {
        int ketQua = 0;
        try {
            // Bước 1: tạo kết nối đến CSDL
            Connection con = JDBCUtil.getConnection();

            // Bước 2: tạo ra đối tượng statement
            String sql = "DELETE from products " +
                    " WHERE id=?";

            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, product.getId());

            // Bước 3: thực thi câu lệnh SQL
            System.out.println(sql);
            ketQua = st.executeUpdate();

            // Bước 4:
            System.out.println("Bạn đã thực thi: " + sql);
            System.out.println("Có " + ketQua + " dòng bị thay đổi!");

            // Bước 5:
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ketQua;
    }

    @Override
    public int update(Product product) {
        int ketQua = 0;
        try {
            // Bước 1: tạo kết nối đến CSDL
            Connection con = JDBCUtil.getConnection();

            // Bước 2: tạo ra đối tượng statement
            String sql = "UPDATE products " +
                    " SET " +
                    " name=?" + "," +
                    " price=?" + "," +
                    " product_type_id=?" + "," +
                    " quantity=?" + "," +
                    " producer_id=?" + "," +
                    " image=?" +
                    " WHERE id=?";

            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, product.getName());
            st.setDouble(2, product.getPrice());
            st.setString(3, product.getProductType().getId());
            st.setInt(4, product.getQuantity());
            st.setString(5, product.getProducer().getId());
            st.setString(6, product.getImg());
            st.setString(7, product.getId());

            // Bước 3: thực thi câu lệnh SQL

            System.out.println(sql);
            ketQua = st.executeUpdate();

            // Bước 4:
            System.out.println("Bạn đã thực thi: " + sql);
            System.out.println("Có " + ketQua + " dòng bị thay đổi!");

            // Bước 5:
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ketQua;
    }

    public ArrayList<Product> selectByIdProducer(String idProducer) {
        ArrayList<Product> ketQua = new ArrayList();
        try {
            // Bước 1: tạo kết nối đến CSDL
            Connection con = JDBCUtil.getConnection();

            // Bước 2: tạo ra đối tượng statement
            String sql = "SELECT id, name, price, product_type_id, quantity, producer_id, " +
                    "image FROM products WHERE producer_id='" + idProducer + "'";
            PreparedStatement st = con.prepareStatement(sql);

            // Bước 3: thực thi câu lệnh SQL
            System.out.println(sql);
            ResultSet rs = st.executeQuery();

            // Bước 4:
            while (rs.next()) {
                Product pro = new Product();
                pro.setId(rs.getString("id"));
                pro.setName(rs.getString("name"));
                pro.setPrice(rs.getDouble("price"));
                ProductTypeDAO productType = new ProductTypeDAO();
                pro.setProductType(productType.selectById(new ProductType(rs.getString("product_type_id"), null)));
                pro.setQuantity(rs.getInt("quantity"));
                ProducerDAO producer = new ProducerDAO();
                pro.setProducer(producer.selectById(new Producer(rs.getString("producer_id"), null)));
                pro.setImg(rs.getString("image"));

                ketQua.add(pro);
            }

            // Bước 5:
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ketQua;
    }


    public int getNumberPage() {
        int ketQua = 0;
        try {
            // Bước 1: tạo kết nối đến CSDL
            Connection con = JDBCUtil.getConnection();

            // Bước 2: tạo ra đối tượng statement
            String sql = "SELECT count(id) FROM products";
            PreparedStatement st = con.prepareStatement(sql);

            // Bước 3: thực thi câu lệnh SQL
            System.out.println(sql);
            ResultSet rs = st.executeQuery();

            // Bước 4:
            while (rs.next()) {
                int total = rs.getInt(1);
                int countPage = 0;
                countPage = total / 20;
                if (total % 20 != 0) {
                    countPage++;
                }
                return countPage;
            }

            // Bước 5:
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ketQua;
    }

    public int getNumberPageByIdProducer(String idProducer) {
        int ketQua = 0;
        try {
            // Bước 1: tạo kết nối đến CSDL
            Connection con = JDBCUtil.getConnection();

            // Bước 2: tạo ra đối tượng statement
            String sql = "SELECT count(id) FROM products WHERE producer_id = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, idProducer);
            // Bước 3: thực thi câu lệnh SQL
            System.out.println(sql);
            ResultSet rs = st.executeQuery();


            // Bước 4:
            while (rs.next()) {
                int total = rs.getInt(1);
                int countPage = 0;
                countPage = total / 20;
                if (total % 20 != 0) {
                    countPage++;
                }
                return countPage;
            }

            // Bước 5:
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ketQua;
    }

    public int getNumberPageByIdProductType() {
        int ketQua = 0;
        try {
            // Bước 1: tạo kết nối đến CSDL
            Connection con = JDBCUtil.getConnection();

            // Bước 2: tạo ra đối tượng statement
            String sql = "SELECT count(id) FROM products WHERE product_type_id=?";
            PreparedStatement st = con.prepareStatement(sql);

            // Bước 3: thực thi câu lệnh SQL
            System.out.println(sql);
            ResultSet rs = st.executeQuery();

            // Bước 4:
            while (rs.next()) {
                int total = rs.getInt(1);
                int countPage = 0;
                countPage = total / 20;
                if (total % 20 != 0) {
                    countPage++;
                }
                return countPage;
            }

            // Bước 5:
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ketQua;
    }


    public ArrayList<Product> getPaging(int index) {
        ArrayList<Product> ketQua = new ArrayList();
        try {
            // Bước 1: tạo kết nối đến CSDL
            Connection con = JDBCUtil.getConnection();

            // Bước 2: tạo ra đối tượng statement
            String sql = "SELECT id, name, price, product_type_id, quantity, producer_id, image FROM products ORDER BY id LIMIT 20 OFFSET ?";
            PreparedStatement st = con.prepareStatement(sql);

            // Bước 3: thực thi câu lệnh SQL
            System.out.println(sql);
            st.setInt(1, (index - 1) * 20);
            ResultSet rs = st.executeQuery();

            // Bước 4:
            while (rs.next()) {
                String id = rs.getString("id");
                String nameProduct = rs.getString("name");
                double price = rs.getDouble("price");
                String productType_id = rs.getString("product_type_id");
                int quantity = rs.getInt("quantity");
                String producer_id = rs.getString("producer_id");
                String img = rs.getString("image");


                Producer producer = (new ProducerDAO().selectById(new Producer(producer_id, null)));
                ProductType productType = new ProductTypeDAO().selectById(new ProductType(productType_id, null));
                Product product = new Product(id, nameProduct, price, productType, quantity, producer, img);
                ketQua.add(product);
            }

            // Bước 5:
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ketQua;
    }

    public ArrayList<Product> getPagingProducer(String idProducer, int index) {
        ArrayList<Product> ketQua = new ArrayList();
        try {
            // Bước 1: tạo kết nối đến CSDL
            Connection con = JDBCUtil.getConnection();

            // Bước 2: tạo ra đối tượng statement
            String sql = "SELECT id, name, price, product_type_id, quantity, producer_id, image FROM products WHERE producer_id = ? ORDER BY id LIMIT 20 OFFSET ?";
            PreparedStatement st = con.prepareStatement(sql);

            // Bước 3: thực thi câu lệnh SQL
            System.out.println(sql);
            st.setString(1, idProducer);
            st.setInt(2, (index - 1) * 20);
            ResultSet rs = st.executeQuery();

            // Bước 4:
            while (rs.next()) {
                String id = rs.getString("id");
                String nameProduct = rs.getString("name");
                double price = rs.getDouble("price");
                String productType_id = rs.getString("product_type_id");
                int quantity = rs.getInt("quantity");
                String producer_id = rs.getString("producer_id");
                String img = rs.getString("image");


                Producer producer = (new ProducerDAO().selectById(new Producer(producer_id, null)));
                ProductType productType = new ProductTypeDAO().selectById(new ProductType(productType_id, null));
                Product product = new Product(id, nameProduct, price, productType, quantity, producer, img);
                ketQua.add(product);
            }

            // Bước 5:
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ketQua;
    }

//    public ArrayList<Product> getPagingProductType(int index) {
//        ArrayList<Product> ketQua = new ArrayList();
//        try {
//            // Bước 1: tạo kết nối đến CSDL
//            Connection con = JDBCUtil.getConnection();
//
//            // Bước 2: tạo ra đối tượng statement
//            String sql = "SELECT id, name, price, product_type_id, quantity, producer_id, image FROM products WHERE product_type_id = ? ORDER BY id LIMIT 20 OFFSET ?";
//            PreparedStatement st = con.prepareStatement(sql);
//
//            // Bước 3: thực thi câu lệnh SQL
//            System.out.println(sql);
//            st.setString(1, new Product().getProductType().getId());
//            st.setInt(2, (index - 1)*20);
//            ResultSet rs = st.executeQuery();
//
//            // Bước 4:
//            while (rs.next()) {
//                String id = rs.getString("id");
//                String nameProduct = rs.getString("name");
//                double price = rs.getDouble("price");
//                String productType_id = rs.getString("product_type_id");
//                int quantity = rs.getInt("quantity");
//                String producer_id = rs.getString("producer_id");
//                String img = rs.getString("image");
//
//
//                Producer producer = (new ProducerDAO().selectById(new Producer(producer_id, null)));
//                ProductType productType = new ProductTypeDAO().selectById(new ProductType(productType_id, null));
//                Product product = new Product(id, nameProduct, price, productType, quantity, producer, img);
//                ketQua.add(product);
//            }
//
//            // Bước 5:
//            JDBCUtil.closeConnection(con);
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        return ketQua;
//    }


    public ArrayList<Product> selectByIdProductType(String idProductType) {
        ArrayList<Product> ketQua = new ArrayList();
        try {
            // Bước 1: tạo kết nối đến CSDL
            Connection con = JDBCUtil.getConnection();

            // Bước 2: tạo ra đối tượng statement
            String sql = "SELECT id, name, price, product_type_id, quantity, producer_id, " +
                    "image FROM products WHERE product_type_id='" + idProductType + "'";
            PreparedStatement st = con.prepareStatement(sql);

            // Bước 3: thực thi câu lệnh SQL
            System.out.println(sql);
            ResultSet rs = st.executeQuery();

            // Bước 4:
            while (rs.next()) {
                Product pro = new Product();
                pro.setId(rs.getString("id"));
                pro.setName(rs.getString("name"));
                pro.setPrice(rs.getDouble("price"));
                ProductTypeDAO productType = new ProductTypeDAO();
                pro.setProductType(productType.selectById(new ProductType(rs.getString("product_type_id"), null)));
                pro.setQuantity(rs.getInt("quantity"));
                ProducerDAO producer = new ProducerDAO();
                pro.setProducer(producer.selectById(new Producer(rs.getString("producer_id"), null)));
                pro.setImg(rs.getString("image"));

                ketQua.add(pro);
            }

            // Bước 5:
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ketQua;
    }

    public int deleteProductById(String id) {
        int ketQua = 0;
        try {
            // Bước 1: tạo kết nối đến CSDL
            Connection con = JDBCUtil.getConnection();

            // Bước 2: tạo ra đối tượng statement
            String sql = "DELETE from products " +
                    " WHERE id=?";

            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, id);

            // Bước 3: thực thi câu lệnh SQL
            System.out.println(sql);
            ketQua = st.executeUpdate();

            // Bước 4:
            System.out.println("Bạn đã thực thi: " + sql);
            System.out.println("Có " + ketQua + " dòng bị thay đổi!");

            // Bước 5:
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ketQua;
    }
    public static Product selectbyId(String id) {
        Optional<Product> product = JDBIConector.me().withHandle((handle ->
                handle.createQuery("SELECT id, name, price, product_type_id, quantity," +
                                " producer_id, image FROM products WHERE id = ?")
                        .bind(0, id)
                        .map((rs, ctx) -> {
//                            String id = rs.getString("id");
                            String nameProduct = rs.getString("name");
                            double price = rs.getDouble("price");
                            String productType_id = rs.getString("product_type_id");
                            int quantity = rs.getInt("quantity");
                            String producer_id = rs.getString("producer_id");
                            String img = rs.getString("image");
                            Producer producer = new ProducerDAO().selectById(new Producer(producer_id, null));
                            ProductType productType = new ProductTypeDAO().selectById(new ProductType(productType_id, null));

                            Product p = new Product(id, nameProduct, price, productType, quantity, producer, img);
                            return p;
                        }).stream().findFirst()
        ));
        return product.isEmpty() ? null : product.get();
    }
    public static void main(String[] args) {
        ProductDAO p = new ProductDAO();
//        System.out.println(searchByName("Loa siêu trầm JBL PASION 12SP 300W"));
//        System.out.println(getById("100"));

    }

}
