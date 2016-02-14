package cz.morosystems.education.vaadinexampleapp.managers;

import java.util.List;

import cz.morosystems.education.vaadinexampleapp.entities.Knowledge;
import cz.morosystems.education.vaadinexampleapp.entities.KnowledgeType;
import cz.morosystems.education.vaadinexampleapp.entities.User;

/**
 * Interface used to interact with user business manager
 * 
 * @author andrej.klima
 * 
 */
public interface UserManager {


    /**
     * Calls UserDao to execute an HQL query and return list of all users
     * 
     * @return list List of all existing user records
     */
    public List<User> findAllUsers();
    /**
     * Calls UserDao to execute an HQL query and return user, specified by ID
     */
    public User findUserById(String id);
    /**
     * Calls UserDao to execute an HQL query and return user, specified by user name
     */
    public User findUserByUserName(String username);

    /**
     * Creates Authority entity for specified user role and persists Authority and User entity
     */
    public void save(User user, String role);
    /**
     * Creates Authority entity for specified user role and updates existing Authority and User record
     */
    public void update(User user, String role);
    /**
     * Uses findUserById to get User record. Gets list of all user's devices and then removes them. Calls UserDao to remove user and user's
     * authority record.
     */
    public void remove(String userId);
    /**
     * Uses UserDao to find user, specified by username. Returns whole user's name.
     */
    public String getWholeNameByUserName(String username);
    /**
     * Finds user record whith specified username and verifies user's password.
     */
    public boolean verifyPassword(String username, String password);
    /**
     * Uses UserDao to update user's record
     */
    public void updateProfile(User user);
    /**
     * Uses UserDao to update user's record
     */
    public void updatePassword(User user);
    /**
     * Uses KnowledgeDao to add user's knowledge
     */
    public void addUserNewKnowledge(String username, Knowledge knowledge);
    /**
     * Uses KnowledgeDao to update user's knowledge
     */
    public void editUserKnowledge(Knowledge knowledge);
    /**
     * Uses KnowledgeDao to remove user's knowledge
     */
    public void removeUserKnowledge(String knowledgeId);
    /**
     * Uses KnowledgeDao to find all knowledge types
     */
    public List<KnowledgeType> findAllKnowledgeTypes();

}
