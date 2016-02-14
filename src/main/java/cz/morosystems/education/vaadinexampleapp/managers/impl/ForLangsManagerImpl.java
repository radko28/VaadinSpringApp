package cz.morosystems.education.vaadinexampleapp.managers.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cz.morosystems.education.vaadinexampleapp.dao.ForLangsDao;
import cz.morosystems.education.vaadinexampleapp.dao.UserDao;
import cz.morosystems.education.vaadinexampleapp.entities.ForeignLanguage;
import cz.morosystems.education.vaadinexampleapp.entities.User;
import cz.morosystems.education.vaadinexampleapp.managers.ForLangsManager;

/**
 * 
 * Logic class for Foreign language.
 * 
 * @author radoslav.kuzma
 * 
 */

@Component
@Scope("prototype")
public class ForLangsManagerImpl implements ForLangsManager, Serializable {


    private static final long serialVersionUID = 7028769774316881591L;
    @Autowired
    ForLangsDao forLangsDao;
    @Autowired
    UserDao userDao;

    /**
     * find language.
     * 
     */
    @Override
    public ForeignLanguage findForeignLanguageById(String id) {
        return forLangsDao.findForeignLanguageById(id);
    }
    /**
     * 
     * find list of languages for user.
     * 
     */
    @Override
    public List<ForeignLanguage> findForeignLanguagesByUserName(String username) {
        User user = userDao.findUserByUserName(username);
        List<ForeignLanguage> result = forLangsDao.findForeignLanguagesByUser(user);
        return result;


    }
    /**
     * 
     * delete language.
     * 
     * 
     */
    public void deleteForeignLanguage(String id) {
        ForeignLanguage foreignLanguage = forLangsDao.findForeignLanguageById(id);
        forLangsDao.remove(foreignLanguage);
    }
    /**
     * 
     * create new language.
     * 
     */
    @Override
    public void save(ForeignLanguage foreingLanguage, String username) {
        User user = userDao.findUserByUserName(username);
        foreingLanguage.setUsers(user);
        foreingLanguage.setCreated(new org.joda.time.DateTime(new Date()));
        forLangsDao.save(foreingLanguage);

    }
    /**
     * 
     * update existing language.
     * 
     */
    @Override
    public void update(ForeignLanguage foreingLanguage, String username) {
        User user = userDao.findUserByUserName(username);
        foreingLanguage.setUsers(user);
        ForeignLanguage record = forLangsDao.findForeignLanguageById(foreingLanguage.getId());
        foreingLanguage.setCreated(record.getCreated());
        forLangsDao.update(foreingLanguage);

    }


}
