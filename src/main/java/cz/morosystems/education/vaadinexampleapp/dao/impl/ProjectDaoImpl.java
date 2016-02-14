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

import cz.morosystems.education.vaadinexampleapp.dao.ProjectDao;
import cz.morosystems.education.vaadinexampleapp.entities.Project;
import cz.morosystems.education.vaadinexampleapp.entities.User;

/**
 * 
 * @author radoslav.kuzma
 * 
 */
@Repository("projectDao")
public class ProjectDaoImpl implements ProjectDao {

    private HibernateTemplate hibernateTemplate;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }


    @Override
    @Transactional
    public void save(final Project project) {
        hibernateTemplate.execute(new HibernateCallback<Void>() {

            @Override
            public Void doInHibernate(Session session) throws HibernateException, SQLException {
                session.save(project);
                return null;
            }
        });
    }


    @Override
    public List<Project> findProjectsByUser(final User user) {
        @SuppressWarnings("unchecked")
        List<Project> result = hibernateTemplate.executeFind(new HibernateCallback<List<Project>>() {

            @Override
            public List<Project> doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuffer hql = new StringBuffer();
                hql.append("FROM Project record left join fetch record.customerArea as customerArea WHERE record.users = :user");
                Query query = session.createQuery(hql.toString());
                query.setEntity("user", user);
                return (List<Project>)query.list();
            }
        });

        return result;

    }


    @Override
    public List<Project> findProjectsRoleByUser(final User user) {
        @SuppressWarnings("unchecked")
        List<Project> result = hibernateTemplate.executeFind(new HibernateCallback<List<Project>>() {

            @Override
            public List<Project> doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuffer hql = new StringBuffer();
                hql.append("FROM Project record left join fetch record.projectRoles as projectRoles WHERE record.users = :user");
                Query query = session.createQuery(hql.toString());
                query.setEntity("user", user);
                return (List<Project>)query.list();
            }
        });

        return result;

    }


    @Override
    public Project findProjectById(final String id) {
        Project result = hibernateTemplate.execute(new HibernateCallback<Project>() {

            @Override
            public Project doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuffer hql = new StringBuffer();
                hql.append("FROM Project WHERE id = :id");
                Query query = session.createQuery(hql.toString());
                query.setString("id", id);
                return (Project)query.uniqueResult();
            }
        });

        return result;
    }


    @Override
    public void remove(final Project project) {
        hibernateTemplate.execute(new HibernateCallback<Object>() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                session.delete(project);
                return null;
            }
        });


    }


    @Override
    public Project findCustomerAreaById(final String id) {
        Project result = hibernateTemplate.execute(new HibernateCallback<Project>() {

            @Override
            public Project doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuffer hql = new StringBuffer();
                hql.append("FROM Project record  left join fetch record.customerArea as customerArea WHERE record.id = :id");
                Query query = session.createQuery(hql.toString());
                query.setString("id", id);
                return (Project)query.uniqueResult();
            }
        });

        return result;

    }


    @Override
    public Project findProjectRoleById(final String id) {
        Project result = hibernateTemplate.execute(new HibernateCallback<Project>() {

            @Override
            public Project doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuffer hql = new StringBuffer();
                hql.append("FROM Project record left join fetch record.projectRoles as projectRoles WHERE record.id = :id");
                Query query = session.createQuery(hql.toString());
                query.setString("id", id);
                return (Project)query.uniqueResult();
            }
        });

        return result;
    }


    @Override
    public void update(final Project project) {
        hibernateTemplate.execute(new HibernateCallback<Object>() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {

                session.update(project);
                return null;
            }
        });


    }


}
