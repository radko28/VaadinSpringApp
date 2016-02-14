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

import cz.morosystems.education.vaadinexampleapp.dao.ProjectRoleDao;
import cz.morosystems.education.vaadinexampleapp.entities.ProjectRole;
import cz.morosystems.education.vaadinexampleapp.entities.ProjectRoleType;

@Repository("projectRoleDao")
public class ProjectRoleDaoImpl implements ProjectRoleDao {

    private HibernateTemplate hibernateTemplate;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    public List<ProjectRole> findAll() {
        List<ProjectRole> result = hibernateTemplate.executeFind(new HibernateCallback<List<ProjectRole>>() {

            @Override
            public List<ProjectRole> doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery("from " + ProjectRole.class.getName() + " u ORDER BY u.name desc");
                return (List<ProjectRole>)query.list();
            }
        });

        return result;
    }

    @Override
    public ProjectRole findProjectRoleByType(final ProjectRoleType projectRoleType) {
        ProjectRole result = hibernateTemplate.execute(new HibernateCallback<ProjectRole>() {

            @Override
            public ProjectRole doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuffer hql = new StringBuffer();
                hql.append("FROM ProjectRole WHERE projectRoleType = :projectRoleType");
                Query query = session.createQuery(hql.toString());
                query.setString("projectRoleType", projectRoleType.toString());
                return (ProjectRole)query.uniqueResult();
            }
        });

        return result;
    }

    @Override
    public ProjectRole findProjectRoleById(final String id) {
        ProjectRole result = hibernateTemplate.execute(new HibernateCallback<ProjectRole>() {

            @Override
            public ProjectRole doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuffer hql = new StringBuffer();
                hql.append("FROM ProjectRole record WHERE record.id = :id");
                Query query = session.createQuery(hql.toString());
                query.setString("id", id);
                return (ProjectRole)query.uniqueResult();
            }
        });

        return result;

    }

    @Override
    public void remove(final ProjectRole projectRole) {
        hibernateTemplate.execute(new HibernateCallback<Object>() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                session.delete(projectRole);
                return null;
            }
        });


    }

    @Override
    public void save(final ProjectRole projectRole) {
        hibernateTemplate.execute(new HibernateCallback<Void>() {

            @Override
            public Void doInHibernate(Session session) throws HibernateException, SQLException {
                session.save(projectRole);
                return null;
            }
        });

    }

    @Override
    public void update(final ProjectRole projectRole) {
        hibernateTemplate.execute(new HibernateCallback<Object>() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {

                session.update(projectRole);
                return null;
            }
        });


    }


}
