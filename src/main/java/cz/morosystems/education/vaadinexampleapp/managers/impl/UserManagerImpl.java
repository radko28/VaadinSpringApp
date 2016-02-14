package cz.morosystems.education.vaadinexampleapp.managers.impl;

import java.io.Serializable;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Component;

import cz.morosystems.education.vaadinexampleapp.dao.DeviceDao;
import cz.morosystems.education.vaadinexampleapp.dao.ForLangsDao;
import cz.morosystems.education.vaadinexampleapp.dao.KnowledgeDao;
import cz.morosystems.education.vaadinexampleapp.dao.UserDao;
import cz.morosystems.education.vaadinexampleapp.entities.Authority;
import cz.morosystems.education.vaadinexampleapp.entities.Device;
import cz.morosystems.education.vaadinexampleapp.entities.ForeignLanguage;
import cz.morosystems.education.vaadinexampleapp.entities.Knowledge;
import cz.morosystems.education.vaadinexampleapp.entities.KnowledgeType;
import cz.morosystems.education.vaadinexampleapp.entities.RoleType;
import cz.morosystems.education.vaadinexampleapp.entities.User;
import cz.morosystems.education.vaadinexampleapp.managers.UserManager;

/**
 * UserManager interface implementation.
 * 
 * @author andrej.klima
 * 
 */

@Component
@Scope("prototype")
public class UserManagerImpl implements UserManager, Serializable {

    private static final long serialVersionUID = 390565815928479750L;
    /** Spring component scan injects UserDao bean */
    @Autowired
    UserDao userDao;
    /** Spring component scan injects DeviceDao bean */
    @Autowired
    DeviceDao deviceDao;
    /** Spring component scan injects KnowledgeDao bean */
    @Autowired
    KnowledgeDao knowledgeDao;

    @Autowired
    ForLangsDao forLangsDao;

    private String encode(String val, String salt) {
        ShaPasswordEncoder encoder = new ShaPasswordEncoder();
        return encoder.encodePassword(val, salt);
    }

    /**
     * Calls UserDao to execute an HQL query and return list of all users
     */
    @Override
    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    /**
     * Calls UserDao findUserById to execute an HQL query and return user, specified by ID
     */
    @Override
    public User findUserById(String id) {
        return userDao.findUserById(id);
    }

    /**
     * Creates Authority entity for specified user role. Sets user created date for now and sets user's enabled to true. Calls UserDao to
     * persist Authority and User entity
     */
    @Override
    public void save(User user, String role) {
        Authority a = new Authority();
        if ("true".equals(role)) {
            a.setAuthority(RoleType.ROLE_ADMIN);
        } else {
            a.setAuthority(RoleType.ROLE_USER);
        }
        user.setBirthdate(user.getBirthdate());
        a.setUsername(user.getUsername());
        a.setUsers(user);
        String encode = encode(user.getPassword(), user.getUsername());
        user.setPassword(encode);
        user.setCreated(DateTime.now());
        user.setEnabled(true);

        userDao.save(user, a);
    }

    /**
     * Creates Authority entity for specified user role and updates existing authority record and User record
     */
    @Override
    public void update(User user, String role) {
        User record = userDao.findUserById(user.getUserId());
        Authority a = new Authority();
        if ("true".equals(role)) {
            a.setAuthority(RoleType.ROLE_ADMIN);
        } else {
            a.setAuthority(RoleType.ROLE_USER);
        }
        record.setBirthdate(user.getBirthdate());
        a.setUsername(user.getUsername());
        a.setUsers(record);
        record.setAuthorities(a);
        record.setFirstname(user.getFirstname());
        record.setLastname(user.getLastname());
        record.setEmail(user.getEmail());
        record.setPhoto(user.getPhoto());
        if (null != user.getPassword() && user.getPassword().length() > 0) {
            String encode = encode(user.getPassword(), user.getUsername());
            record.setPassword(encode);
        }

        record.setCreated(DateTime.now());
        record.setEnabled(true);

        userDao.update(record, a);
    }

    /**
     * Uses findUserById to get User entity from database. Calls DeviceDao findDevicesByUser method to get list of user's devices and remove
     * method to remove them. Uses UserDao remove method to remove user and user's authority record. Finally calls userDao to return list of
     * all remaining users.
     */
    @Override
    public void remove(String userId) {
        User user = userDao.findUserById(userId);
        List<Device> deviceList = deviceDao.findDevicesByUser(user);
        for (Device device : deviceList) {
            deviceDao.remove(device);
        }
        List<ForeignLanguage> forLangsList = forLangsDao.findForeignLanguagesByUser(user);
        for (ForeignLanguage foreignLanguage : forLangsList) {
            forLangsDao.remove(foreignLanguage);
        }
        userDao.remove(userId);
    }

    /**
     * Calls UserDao to execute an HQL query and return user, specified by user name
     */
    @Override
    public User findUserByUserName(String username) {
        return userDao.findUserByUserName(username);
    }
    /**
     * Uses UserDao to find user, specified by username. Creates and returns whole user's name string.
     * 
     * @return the string, concatenation of user's firstname, whitespace and user's lastname
     */
    @Override
    public String getWholeNameByUserName(String username) {
        User user = userDao.findUserByUserName(username);
        String wholeName = user.getFirstname() + " " + user.getLastname();
        return wholeName;
    }
    /**
     * Finds user record whith specified username and verifies user's password.
     */
    @Override
    public boolean verifyPassword(String username, String password) {
        User record = userDao.findUserByUserName(username);
        ShaPasswordEncoder coder = new ShaPasswordEncoder();
        return coder.isPasswordValid(record.getPassword(), password, username);
    }
    /**
     * Uses findUserById to get User entity from database. Sets entity firstname, lastname and birthday property to parameter firstname,
     * lastname and birthdateString value. Uses UserDao to update user's record
     */
    @Override
    public void updateProfile(User user) {
        User u = findUserById(user.getUserId());
        u.setFirstname(user.getFirstname());
        u.setLastname(user.getLastname());
        u.setBirthdate(user.getBirthdate());
        u.setEmail(user.getEmail());
        u.setPhoto(user.getPhoto());
        userDao.update(u);
    }
    /**
     * Uses findUserById to get User entity from database. Sets entity password property to parameter password property value. Calls UserDao
     * update to update record of user.
     */
    @Override
    public void updatePassword(User user) {
        User u = findUserById(user.getUserId());
        u.setPassword(encode(user.getPassword(), user.getUsername()));
        userDao.update(u);
    }
    /**
     * Uses findUserByUserName to get User entity from database. Set knowledge's user property with user entity. Uses KnowledgeDao to create
     * knowledge record.
     */
    @Override
    public void addUserNewKnowledge(String username, Knowledge knowledge) {
        User user = userDao.findUserByUserName(username);
        knowledge.setUsers(user);
        knowledgeDao.save(knowledge);
    }
    /**
     * Uses KnowledgeDao to update user's knowledge
     */
    @Override
    public void editUserKnowledge(Knowledge knowledge) {
        knowledgeDao.update(knowledge);
    }
    /**
     * Uses KnowledgeDao to remove user's knowledge
     */
    @Override
    public void removeUserKnowledge(String knowledgeId) {
        knowledgeDao.remove(knowledgeId);
    }
    /**
     * Uses KnowledgeDao to find all knowledge types
     */
    @Override
    public List<KnowledgeType> findAllKnowledgeTypes() {
        return knowledgeDao.findAll();
    }
}
