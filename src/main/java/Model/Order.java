package Model;

import java.sql.Date;

public class Order {
    private String id;
    private User user;
    private String address;
    private String status;
    private String payMent;
    private Date orderDate;
    private Date deliveryDate;

    public Order() {
    }

    public Order(String id, User user, String address, String status, String payMent, Date orderDate, Date deliveryDate) {
        this.id = id;
        this.user = user;
        this.address = address;
        this.status = status;
        this.payMent = payMent;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayMent() {
        return payMent;
    }

    public void setPayMent(String payMent) {
        this.payMent = payMent;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                ", payMent='" + payMent + '\'' +
                ", orderDate=" + orderDate +
                ", deliveryDate=" + deliveryDate +
                '}';
    }

}
