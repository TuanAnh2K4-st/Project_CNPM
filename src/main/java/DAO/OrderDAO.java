package DAO;

import Model.*;
import db.JDBIConector;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDAO implements DAOInterface<Order> {
    @Override
    public List<Order> selectAll() {
        List<Order> ketQua = JDBIConector.me().withHandle((handle ->
        {
            List<Order> orders = new ArrayList<>();

            handle.createQuery("SELECT id, user_id, delivery_address, order_status, payment_method, " +
                            "order_date, delivery_date FROM orders")
                    .map((rs, ctx) -> {
                        String id = rs.getString("id");
                        String userId = rs.getString("user_id");
                        String deliveryAddress = rs.getString("delivery_address");
                        String orderStatus = rs.getString("order_status");
                        String paymentMethod = rs.getString("payment_method");
                        Date orderDate = rs.getDate("order_date");
                        Date deliveryDate = rs.getDate("delivery_date");

                        User user = new UserDAO().selectById(new User(userId, null, null, null, null,
                                null, null, null, null,null));

                        Order order = new Order(id, user, deliveryAddress, orderStatus, paymentMethod, orderDate, deliveryDate);
                        orders.add(order);

                        return null;
                    })
                    .list();

            return orders;
        }));

        return ketQua;
    }
    @Override
    public Order selectById(Order orderP) {
        try {
            return JDBIConector.me().withHandle(handle ->
                    handle.createQuery("SELECT id, user_id, delivery_address, order_status," +
                                    " payment_method, order_date, delivery_date FROM orders WHERE id=?")
                            .bind(0, orderP.getId())
                            .map((rs, ctx) -> {
                                String orderId = rs.getString("id");
                                String userId = rs.getString("user_id");
                                String address = rs.getString("delivery_address");
                                String orderStatus = rs.getString("order_status");
                                String paymentMethod = rs.getString("payment_method");
                                Date orderDate = rs.getDate("order_date");
                                Date deliveryDate = rs.getDate("delivery_date");

                                User user = new UserDAO().selectById(new User(userId, null, null, null, null, null, null, null, null, null));
                                return new Order(orderId, user, address, orderStatus, paymentMethod, orderDate, deliveryDate);
                            })
                            .findFirst()
                            .orElse(null)
            );
        } catch (Exception e) {
            e.printStackTrace(); // In ra lỗi để debug
            return null;
        }
    }

    public static Order getById(String id) {
        try {
            return JDBIConector.me().withHandle(handle ->
                    handle.createQuery("SELECT id, user_id, delivery_address, order_status," +
                                    " payment_method, order_date, delivery_date FROM orders WHERE id=?")
                            .bind(0, id)
                            .map((rs, ctx) -> {
                                String orderId = rs.getString("id");
                                String userId = rs.getString("user_id");
                                String address = rs.getString("delivery_address");
                                String orderStatus = rs.getString("order_status");
                                String paymentMethod = rs.getString("payment_method");
                                Date orderDate = rs.getDate("order_date");
                                Date deliveryDate = rs.getDate("delivery_date");

                                User user = new UserDAO().selectById(new User(userId, null, null, null, null, null, null, null, null, null));
                                return new Order(orderId, user, address, orderStatus, paymentMethod, orderDate, deliveryDate);
                            })
                            .findFirst()
                            .orElse(null)
            );
        } catch (Exception e) {
            e.printStackTrace(); // In ra lỗi để debug
            return null;
        }
    }





    @Override
    public int insert(Order order) {
        int ketQua = 0;
        try {
            // Bước 1: tạo kết nối đến CSDL
            Connection con = JDBCUtil.getConnection();

            // Bước 2: tạo ra đối tượng statement
            String sql = "INSERT INTO orders (id, user_id, delivery_address, order_status, payment_method, order_date, delivery_date) " +
                    " VALUES (?,?,?,?,?,?,?)";

            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, order.getId());
            if (order.getUser() != null) {
                st.setString(2, order.getUser().getId());
            } else {
                st.setNull(2, Types.VARCHAR);
            }
            st.setString(3, order.getAddress());
            st.setString(4, order.getStatus());
            st.setString(5, order.getPayMent());
            st.setDate(6, order.getOrderDate());
            st.setDate(7, order.getDeliveryDate());


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
    public int delete(Order order) {
        int ketQua = 0;
        try {
            // Bước 1: tạo kết nối đến CSDL
            Connection con = JDBCUtil.getConnection();

            // Bước 2: tạo ra đối tượng statement
            String sql = "DELETE from orders "+
                    " WHERE id=?";

            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, order.getId());

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
    public int update(Order order) {

        int ketQua = 0;
        try {
            // Bước 1: tạo kết nối đến CSDL
            Connection con = JDBCUtil.getConnection();

            // Bước 2: tạo ra đối tượng statement
            String sql = "UPDATE orders "+
                    " SET " +
                    " user_id=?"+","+
                    " delivery_address=?"+","+
                    " order_status=?"+","+
                    " payment_method=?"+","+
                    " order_date=?"+","+
                    " delivery_date=?"+
                    " WHERE id=?";
            PreparedStatement st = con.prepareStatement(sql);

            st.setString(1, order.getUser().getId());
            st.setString(2, order.getAddress());
            st.setString(3, order.getStatus());
            st.setString(4, order.getPayMent());
            st.setDate(5, order.getOrderDate());
            st.setDate(6, order.getDeliveryDate());
            st.setString(7, order.getId());


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

    public int countOrdersInMonth(LocalDate localDate) {
        int count = 0;
        try {
            // Bước 1: tạo kết nối đến CSDL
            Connection con = JDBCUtil.getConnection();

            // Bước 2: tạo ra đối tượng statement
            String sql = "SELECT COUNT(*) FROM orders WHERE MONTH(order_date) = ? AND YEAR(order_date) = ?";
            PreparedStatement st = con.prepareStatement(sql);

            st.setInt(1, localDate.getMonthValue());
            st.setInt(2, localDate.getYear());

            // Bước 3: thực thi câu lệnh SQL
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }

            // Bước 4:
            System.out.println("Bạn đã thực thi: " + sql);
            System.out.println("Số lượng đơn hàng trong tháng: " + count);

            // Bước 5:
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }
    public int countCustomersWithOrdersInMonth(LocalDate localDate) {
        int count = 0;
        try {
            // Bước 1: tạo kết nối đến CSDL
            Connection con = JDBCUtil.getConnection();

            // Bước 2: tạo ra đối tượng statement
            String sql = "SELECT COUNT(DISTINCT user_id) FROM orders WHERE MONTH(order_date) = ? AND YEAR(order_date) = ?";
            PreparedStatement st = con.prepareStatement(sql);

            st.setInt(1, localDate.getMonthValue());
            st.setInt(2, localDate.getYear());

            // Bước 3: thực thi câu lệnh SQL
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }

            // Bước 4:
            System.out.println("Bạn đã thực thi: " + sql);
            System.out.println("Số lượng khách hàng đã mua hàng trong tháng: " + count);

            // Bước 5:
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public static void main(String[] args) {
        OrderDAO orderDAO = new OrderDAO();

       List<Order> a = orderDAO.selectAll();
       for (Order o : a){
           String u = o.getUser().getId();
       }
        System.out.println();

    }
}
