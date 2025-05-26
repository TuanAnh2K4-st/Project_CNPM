package DAO;

import Model.*;
import db.JDBIConector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO implements DAOInterface<User> {
    @Override
    public List<User> selectAll() {
        List<User> ketQua = JDBIConector.me().withHandle((handle ->
        {
            List<User> users = new ArrayList<>();
            handle.createQuery("SELECT id, name, sex, address, birth_day, phone_number, email, user_name, password, role_id FROM users")
                    .map((rs, ctx) -> {
                        String id = rs.getString("id");
                        String name = rs.getString("name");
                        String sex = rs.getString("sex");
                        String address = rs.getString("address");
                        Date birth_day = rs.getDate("birth_day");
                        String phone_number = rs.getString("phone_number");
                        String email = rs.getString("email");
                        String user_name = rs.getString("user_name");
                        String password = rs.getString("password");
                        String role_id = rs.getString("role_id");

                        Role role = new RoleDAO().selectById(new Role(role_id, null));

                        User user = new User(id, name, sex, address, birth_day, phone_number, email, user_name, password, role);
                        users.add(user);

                        return null;
                    }).list();
            return users;
        }));
        return ketQua;
    }
    public User getUserByEmail(String email) {
        Optional<User> user = JDBIConector.me().withHandle(handle ->
                handle.createQuery("SELECT id, name, sex, address, birth_day, phone_number, " +
                                "email, user_name, password, role_id FROM users WHERE email = ?")
                        .bind(0, email)
                        .map((rs, ctx) -> {
                            String id = rs.getString("id");
                            String name = rs.getString("name");
                            String sex = rs.getString("sex");
                            String address = rs.getString("address");
                            Date birth_day = rs.getDate("birth_day");
                            String phone_number = rs.getString("phone_number");
                            String email1 = rs.getString("email");
                            String user_name = rs.getString("user_name");
                            String password = rs.getString("password");
                            String role_id = rs.getString("role_id");

                            Role role = new RoleDAO().selectById(new Role(role_id, null));
                            return new User(id, name, sex, address, birth_day, phone_number, email1, user_name, password, role);
                        })
                        .findFirst()
        );

        return user.orElse(null);
    }


    @Override
    public User selectById(User userP) {
        Optional<User> user = JDBIConector.me().withHandle((handle ->
                handle.createQuery("SELECT id, name, sex, address, birth_day, phone_number, " +
                                "email, user_name, password, role_id FROM users WHERE id = ?")
                        .bind(0, userP.getId())
                        .map((rs, ctx) -> {
                            String id = rs.getString("id");
                            String name = rs.getString("name");
                            String sex = rs.getString("sex");
                            String address = rs.getString("address");
                            Date birth_day = rs.getDate("birth_day");
                            String phone_number = rs.getString("phone_number");
                            String email1 = rs.getString("email");
                            String user_name = rs.getString("user_name");
                            String password = rs.getString("password");
                            String role_id = rs.getString("role_id");

                            Role role = new RoleDAO().selectById(new Role(role_id, null));
                            return new User(id, name, sex, address, birth_day, phone_number, email1, user_name, password, role);
                        })
                        .findFirst()
        ));
        return user.isEmpty() ? null : user.get();
    }
    public User getById(String userP) {
        Optional<User> user = JDBIConector.me().withHandle((handle ->
                handle.createQuery("SELECT id, name, sex, address, birth_day, phone_number, " +
                                "email, user_name, password, role_id FROM users WHERE id = ?")
                        .bind(0, userP)
                        .map((rs, ctx) -> {
                            String id = rs.getString("id");
                            String name = rs.getString("name");
                            String sex = rs.getString("sex");
                            String address = rs.getString("address");
                            Date birth_day = rs.getDate("birth_day");
                            String phone_number = rs.getString("phone_number");
                            String email1 = rs.getString("email");
                            String user_name = rs.getString("user_name");
                            String password = rs.getString("password");
                            String role_id = rs.getString("role_id");

                            Role role = new RoleDAO().selectById(new Role(role_id, null));
                            return new User(id, name, sex, address, birth_day, phone_number, email1, user_name, password, role);
                        })
                        .findFirst()
        ));
        return user.isEmpty() ? null : user.get();
    }


    public static void main(String[] args) {
        UserDAO user = new UserDAO();
        User u = new User("u_2", "hhh", null, null, null,null, "hahuydung@gmail.com", "dung", "123", null);
        //System.out.println(user.selectAll());
        System.out.println(user.getUserByEmail("hadung6765@gmail.com"));
    }
    @Override
    public int insert(User user) {
        int ketQua = 0;
        try {
            // Bước 1: tạo kết nối đến CSDL
            Connection con = JDBCUtil.getConnection();

            // Bước 2: tạo ra đối tượng statement
            String sql = "INSERT INTO users (id, name, sex, address, birth_day, phone_number, email, user_name, password) " +
                    " VALUES (?,?,?,?,?,?,?,?,?)";

            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, user.getId());
            st.setString(2, user.getName());
            st.setString(3, user.getSex());
            st.setString(4, user.getAddress());
            st.setDate(5, user.getBirth_day());
            st.setString(6, user.getPhone_number());
            st.setString(7, user.getEmail());
            st.setString(8, user.getUser_name());
            st.setString(9, user.getPassword());


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
    public int delete(User user) {
        int ketQua = 0;
        try {
            // Bước 1: tạo kết nối đến CSDL
            Connection con = JDBCUtil.getConnection();

            // Bước 2: tạo ra đối tượng statement
            String sql = "DELETE from users "+
                    " WHERE id=?";

            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, user.getId());

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
    public int update(User user) {
        int ketQua = 0;
        try {
            // Bước 1: tạo kết nối đến CSDL
            Connection con = JDBCUtil.getConnection();

            // Bước 2: tạo ra đối tượng statement
            String sql = "UPDATE users "+
                    " SET " +
                    " name=?"+","+
                    " sex=?"+","+
                    " address=?"+","+
                    " birth_day=?"+","+
                    " phone_number=?"+","+
                    " email=?"+","+
                    " user_name=?"+","+
                    " password=?"+
                    " WHERE id=?";
            PreparedStatement st = con.prepareStatement(sql);

            st.setString(1, user.getName());
            st.setString(2, user.getSex());
            st.setString(3, user.getAddress());
            st.setDate(4, user.getBirth_day());
            st.setString(5, user.getPhone_number());
            st.setString(6, user.getEmail());
            st.setString(7, user.getUser_name());
            st.setString(8, user.getPassword());
            st.setString(9, user.getId());



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





}
