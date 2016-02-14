package cz.morosystems.education.vaadinexampleapp.dao;

import java.util.List;

import cz.morosystems.education.vaadinexampleapp.entities.Authority;
import cz.morosystems.education.vaadinexampleapp.entities.User;

/**
 * 
 * Interface for manipulating user data.
 * 
 * @author andrej.klima
 * 
 */

public interface UserDao {

    /**
     * Return list of the all users.
     */
    public List<User> findAll();
    /**
     * Read one user entity from database.
     */
    public User findUserById(String userId);
    /**
     * Create new user entity and his role.
     */
    public void save(User user, Authority role);
    /**
     * Update user entity and his role.
     */
    public void update(User user, Authority role);
    /**
     * Remove user entity and his role.
     */
    public void remove(String userId);
    /**
     * Read user entity by username.
     */
    public User findUserByUserName(String username);
    /**
     * Update user entity.
     */
    public void update(User user);
}
