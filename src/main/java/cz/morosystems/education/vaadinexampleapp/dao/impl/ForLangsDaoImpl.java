package cz.morosystems.education.vaadinexampleapp.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.morosystems.education.vaadinexampleapp.dao.ForLangsDao;
import cz.morosystems.education.vaadinexampleapp.entities.Device;
import cz.morosystems.education.vaadinexampleapp.entities.ForeignLanguage;
import cz.morosystems.education.vaadinexampleapp.entities.User;

/**
 * Dao for foreign language
 * 
 * @author radoslav.kuzma
 * 
 */

@Repository("forLangsDao")
@Transactional
public class ForLangsDaoImpl implements ForLangsDao {

    HibernateTemplate hibernateTemplate;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    /**
     * 
     * Read list of language for user.
     * 
     * 
     */

    @Override
    public List<ForeignLanguage> findForeignLanguagesByUser(final User user) {
        @SuppressWarnings("unchecked")
        List<ForeignLanguage> result = hibernateTemplate.executeFind(new HibernateCallback<List<Device>>() {

            @Override
            public List<Device> doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuffer hql = new StringBuffer();
                hql.append("FROM ForeignLanguage record WHERE record.users = :user");
                Query query = session.createQuery(hql.toString());
                query.setEntity("user", user);
                return (List<Device>)query.list();
            }
        });

        return result;
    }

    /**
     * 
     * Delete language.
     * 
     */
    @Override
    public void remove(final ForeignLanguage foreignLanguage) {
        hibernateTemplate.execute(new HibernateCallback<Object>() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                session.delete(foreignLanguage);
                return null;
            }
        });

    }

    /**
     * 
     * Get language by id.
     * 
     */
    @Override
    public ForeignLanguage findForeignLanguageById(final String id) {
        ForeignLanguage result = (ForeignLanguage)hibernateTemplate.execute(new HibernateCallback<Object>() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuffer hql = new StringBuffer();
                hql.append("FROM ForeignLanguage WHERE id = :id");
                Query query = session.createQuery(hql.toString());
                query.setString("id", id);
                return (ForeignLanguage)query.uniqueResult();
            }
        });

        return result;
    }

    /**
     * 
     * Create new language.
     * 
     */
    @Override
    public void save(final ForeignLanguage foreingLanguage) {
        hibernateTemplate.execute(new HibernateCallback<Object>() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                session.save(foreingLanguage);
                return null;
            }
        });

    }

    /**
     * Update language.
     * 
     */
    @Override
    public void update(final ForeignLanguage foreingLanguage) {
        hibernateTemplate.execute(new HibernateCallback<Object>() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {

                session.update(foreingLanguage);
                return null;
            }
        });


    }


}
