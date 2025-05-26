package Model;

import java.sql.Date;

public class User {
    private String id;
    private String name;
    private String sex;
    private String address;
    private Date birth_day;
    private String phone_number;
    private String email;
    private String user_name;
    private String password;
    private Role role_id;
    private String role_idStr;

    public User() {
    }

    public User(String id, String name, String sex, String address, Date birth_day, String phone_number, String email, String user_name, String password, Role role_id) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.address = address;
        this.birth_day = birth_day;
        this.phone_number = phone_number;
        this.email = email;
        this.user_name = user_name;
        this.password = password;
        this.role_id = role_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirth_day() {
        return birth_day;
    }

    public void setBirth_day(Date birth_day) {
        this.birth_day = birth_day;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole_id() {
        return role_id;
    }

    public void setRole_id(Role role_id) {
        this.role_id = role_id;
    }

    public String getRole_idStr() {
        return role_idStr;
    }

    public void setRole_idStr(String role_idStr) {
        this.role_idStr = role_idStr;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", birth_day=" + birth_day +
                ", phone_number='" + phone_number + '\'' +
                ", email='" + email + '\'' +
                ", user_name='" + user_name + '\'' +
                ", password='" + password + '\'' +
                ", role_id=" + role_id +
                '}';
    }

    public User(String name, String sex, String address, Date birth_day, String phone_number, String email, String user_name, String password, String role_idStr) {
        this.name = name;
        this.sex = sex;
        this.address = address;
        this.birth_day = birth_day;
        this.phone_number = phone_number;
        this.email = email;
        this.user_name = user_name;
        this.password = password;
        this.role_idStr = role_idStr;
    }

    public User(String name, String sex, String address, Date birth_day, String phone_number, String email, String user_name, String password) {
        this.name = name;
        this.sex = sex;
        this.address = address;
        this.birth_day = birth_day;
        this.phone_number = phone_number;
        this.email = email;
        this.user_name = user_name;
        this.password = password;
    }
}
