package service.impl;

import DAO.IRoleDao;
import DAO.IUserDao;
import DAO.impl.roleDaoImpl;
import DAO.impl.userDaoImpl;
import Model.Role;
import Model.User;
import service.IUserService;

import java.util.List;

public class userServiceImpl implements IUserService {
    private IUserDao DAO = new userDaoImpl();
    private IRoleDao roleDao = new roleDaoImpl();

    @Override
    public boolean login(String username, String password) {
        User user = new User();
        user.setUser_name(username);
        user.setPassword(password);
        return DAO.login(user);
    }

    @Override
    public String register(User user) {
        user.setId(createId());
        User userNew = DAO.register(user);
        return userNew == null ? null : userNew.getId();
    }

    @Override
    public boolean isUsernameExists(String username) {
        return DAO.isUsernameExists(username);
    }

    @Override
    public String getIdByUsername(String username) {
        return DAO.getIdByUsername(username);
    }

    @Override
    public User getByUsername(String username) {
        return DAO.getByUsername(username);
    }

    @Override
    public User getById(String id) {
        return DAO.getById(id);
    }

    @Override
    public boolean isEmailExists(String email) {
        return DAO.isEmailExists(email);
    }

    @Override
    public void resetPass(String email, String password) {
        DAO.resetPass(email, password);
    }

    @Override
    public List<User> findAll() {
        return DAO.findAll();
    }

    @Override
    public void deleteById(String id) {
        DAO.deleteById(id);
    }

    @Override
    public void update(User user) {
        DAO.update(user);
    }

    @Override
    public void add(User user, String role) {
        user.setRole_idStr(roleDao.getByName(role).getId());
        user.setId(createId());
        DAO.add(user);
    }

    private String createId(){
        String idOld = DAO.getIdTop1();
        if(idOld == null)
            return "u_1";
        int idNumber = Integer.parseInt(idOld.substring(2)) + 1;
        return "u_" + idNumber;
    }
}
