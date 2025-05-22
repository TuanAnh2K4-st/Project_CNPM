package DAO;

import Model.*;
import db.JDBIConector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProducerDAO implements DAOInterface<Producer> {
    @Override
    public List<Producer> selectAll() {
        List<Producer> producers = JDBIConector.me().withHandle((handle ->
                handle.createQuery("SELECT id, name FROM producers")
                        .mapToBean(Producer.class).stream().collect(Collectors.toList())
        ));
        return producers;
    }

    @Override
    public Producer selectById(Producer producerP) {
        Optional<Producer> producer = JDBIConector.me().withHandle((handle ->
                handle.createQuery("SELECT id, name FROM producers WHERE id=?")
                        .bind(0, producerP.getId())
                        .mapToBean(Producer.class).stream().findFirst()
        ));
        return producer.isEmpty() ? null : producer.get();
    }

    @Override
    public int insert(Producer producer) {
        int ketQua = 0;
        try {
            // Bước 1: tạo kết nối đến CSDL
            Connection con = JDBCUtil.getConnection();

            // Bước 2: tạo ra đối tượng statement
            String sql = "INSERT INTO producers (id, name) " +
                    " VALUES (?,?)";

            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, producer.getId());
            st.setString(2, producer.getName());

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
    public int delete(Producer producer) {
        int ketQua = 0;
        try {
            // Bước 1: tạo kết nối đến CSDL
            Connection con = JDBCUtil.getConnection();

            // Bước 2: tạo ra đối tượng statement
            String sql = "DELETE from producers " +
                    " WHERE id=?";

            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, producer.getId());

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
    public int update(Producer producer) {
        int ketQua = 0;
        try {
            // Bước 1: tạo kết nối đến CSDL
            Connection con = JDBCUtil.getConnection();

            // Bước 2: tạo ra đối tượng statement
            String sql = "UPDATE producers " +
                    " SET " +
                    " name=?" +
                    " WHERE id=?";

            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, producer.getName());
            st.setString(2, producer.getId());

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

    public static Producer getById(String id) {
        Optional<Producer> producer = JDBIConector.me().withHandle((handle ->
                handle.createQuery("SELECT id, name FROM producers WHERE id=?")
                        .bind(0, id)
                        .map((rs, ctx) -> {
                            String nameProducer = rs.getString("name");
                            Producer pc = new Producer(id, nameProducer);
                            return pc;
                        }).stream().findFirst()
        ));
        return producer.isEmpty() ? null : producer.get();
    }

    public static void main(String[] args) {
        ProducerDAO pdd = new ProducerDAO();
//        Producer producer = pdd.selectById(new Producer("AP", null));
//        System.out.println(producer);
        System.out.println(getById("AK"));

    }
}
