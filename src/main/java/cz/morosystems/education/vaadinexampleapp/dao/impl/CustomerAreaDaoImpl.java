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

import cz.morosystems.education.vaadinexampleapp.dao.CustomerAreaDao;
import cz.morosystems.education.vaadinexampleapp.entities.CustomerArea;
import cz.morosystems.education.vaadinexampleapp.entities.CustomerAreaType;

@Repository("customerAreaDao")
public class CustomerAreaDaoImpl implements CustomerAreaDao {

    private HibernateTemplate hibernateTemplate;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }


    @Override
    public List<CustomerArea> findAll() {
        List<CustomerArea> result = hibernateTemplate.executeFind(new HibernateCallback<List<CustomerArea>>() {

            @Override
            public List<CustomerArea> doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery("from " + CustomerArea.class.getName() + " u ORDER BY u.name desc");
                return (List<CustomerArea>)query.list();
            }
        });

        return result;

    }


    @Override
    public CustomerArea findCustomerAreaByType(final CustomerAreaType customerAreaType) {
        CustomerArea result = hibernateTemplate.execute(new HibernateCallback<CustomerArea>() {

            @Override
            public CustomerArea doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuffer hql = new StringBuffer();
                hql.append("FROM CustomerArea WHERE customerAreaType = :customerAreaType");
                Query query = session.createQuery(hql.toString());
                query.setString("customerAreaType", customerAreaType.toString());
                return (CustomerArea)query.uniqueResult();
            }
        });

        return result;

    }


    @Override
    public CustomerArea findCustomerAreaById(final String id) {
        CustomerArea result = hibernateTemplate.execute(new HibernateCallback<CustomerArea>() {

            @Override
            public CustomerArea doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuffer hql = new StringBuffer();
                hql.append("FROM CustomerArea record WHERE record.id = :id");
                Query query = session.createQuery(hql.toString());
                query.setString("id", id);
                return (CustomerArea)query.uniqueResult();
            }
        });

        return result;

    }


    @Override
    public void remove(final CustomerArea customerArea) {
        hibernateTemplate.execute(new HibernateCallback<Object>() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                session.delete(customerArea);
                return null;
            }
        });


    }


    @Override
    public void save(final CustomerArea customerArea) {
        hibernateTemplate.execute(new HibernateCallback<Void>() {

            @Override
            public Void doInHibernate(Session session) throws HibernateException, SQLException {
                session.save(customerArea);
                return null;
            }
        });


    }


    @Override
    public void update(final CustomerArea customerArea) {
        hibernateTemplate.execute(new HibernateCallback<Object>() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {

                session.update(customerArea);
                return null;
            }
        });


    }

}
