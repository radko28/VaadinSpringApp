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

import cz.morosystems.education.vaadinexampleapp.dao.UserDao;
import cz.morosystems.education.vaadinexampleapp.entities.Authority;
import cz.morosystems.education.vaadinexampleapp.entities.User;

/**
 * 
 * Class for manipulating user data.
 * 
 * @author andrej.klima
 * 
 */
@Repository("userDao")
@Transactional
public class UserDaoImpl implements UserDao {

    HibernateTemplate hibernateTemplate;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    /**
     * Return list of the all users, ordered.
     */
    @SuppressWarnings("unchecked")
    public List<User> findAll() {
        List<User> result = hibernateTemplate.executeFind(new HibernateCallback<List<User>>() {

            @Override
            public List<User> doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery("from " + User.class.getName() + " u ORDER BY u.created desc");
                return (List<User>)query.list();
            }
        });

        return result;
    }

    /**
     * Read one user entity from database.
     */
    @Override
    public User findUserById(final String userId) {
        User result = (User)hibernateTemplate.execute(new HibernateCallback<User>() {

            @Override
            public User doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery("FROM User WHERE userId = :userId");
                query.setString("userId", userId);

                return (User)query.uniqueResult();
            }
        });

        return result;
    }

    /**
     * Create new user entity and his role.
     */
    @Transactional(readOnly = false)
    public void save(final User user, final Authority role) {
        hibernateTemplate.execute(new HibernateCallback<Object>() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                session.save(user);
                session.save(role);
                return null;
            }
        });
    }

    /**
     * Remove user entity and his role.
     */
    @Transactional(readOnly = false)
    public void remove(final String userId) {
        hibernateTemplate.execute(new HibernateCallback<Object>() {

            @Override
            public Authority doInHibernate(Session ses) throws HibernateException, SQLException {
                Query q = ses.createQuery("DELETE FROM Authority WHERE user_id like :userId");
                q.setString("userId", userId);
                q.executeUpdate();
                return null;
            }
        });

        hibernateTemplate.execute(new HibernateCallback<Object>() {

            @Override
            public Authority doInHibernate(Session ses) throws HibernateException, SQLException {
                Query q = ses.createQuery("DELETE FROM Knowledge WHERE user_id like :userId");
                q.setString("userId", userId);
                q.executeUpdate();
                return null;
            }
        });

        hibernateTemplate.delete(hibernateTemplate.get(User.class, userId));
    }


    /**
     * Update user entity and his role.
     */
    @Transactional(readOnly = false)
    public void update(final User user, final Authority role) {
        update(user);
        hibernateTemplate.execute(new HibernateCallback<Object>() {

            @Override
            public Object doInHibernate(Session ses) throws HibernateException, SQLException {
                Query q = ses.createQuery("UPDATE Authority SET authority= :authority WHERE user_id like :userId");
                q.setString("authority", role.getAuthority().toString());
                q.setString("userId", user.getUserId());
                q.executeUpdate();
                return null;
            }
        });
    }

    /**
     * Read user entity by username.
     */
    @Override
    public User findUserByUserName(final String username) {
        User result = (User)hibernateTemplate.execute(new HibernateCallback<User>() {

            @Override
            public User doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuffer hql = new StringBuffer();
                hql.append("FROM User WHERE username = :username");
                Query query = session.createQuery(hql.toString());
                query.setString("username", username);
                return (User)query.uniqueResult();
            }
        });

        return result;
    }

    /**
     * Update user entity.
     */
    public void update(final User user) {
        hibernateTemplate.execute(new HibernateCallback<Object>() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                session.update(user);
                return null;
            }
        });
    }

}
