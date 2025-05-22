package DAO;

import Model.Role;

public interface IRoleDao {
    Role getByName(String name);
}
