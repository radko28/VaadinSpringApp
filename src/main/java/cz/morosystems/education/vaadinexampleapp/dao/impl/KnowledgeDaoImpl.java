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

import cz.morosystems.education.vaadinexampleapp.dao.KnowledgeDao;
import cz.morosystems.education.vaadinexampleapp.entities.Knowledge;
import cz.morosystems.education.vaadinexampleapp.entities.KnowledgeType;

/**
 * Class to add, update or remover user's knowledge.
 * 
 * @author andrej.klima
 * 
 */
@Repository("knowledgeDao")
@Transactional
public class KnowledgeDaoImpl implements KnowledgeDao {

    HibernateTemplate hibernateTemplate;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    /**
     * Save knowledge entity
     */
    public void save(Knowledge knowledge) {
        hibernateTemplate.save(knowledge);
    }
    /**
     * Update knowledge entity
     */
    public void update(Knowledge knowledge) {
        hibernateTemplate.update(knowledge);
    }
    /**
     * Remove knowledge entity
     */
    public void remove(final String knowledgeId) {
        hibernateTemplate.execute(new HibernateCallback<Object>() {

            @Override
            public Knowledge doInHibernate(Session ses) throws HibernateException, SQLException {
                Query q = ses.createQuery("DELETE FROM Knowledge WHERE knowledgeId like :knowledgeId");
                q.setString("knowledgeId", knowledgeId);
                q.executeUpdate();
                return null;
            }
        });
    }

    public void saveKnowledgeType(KnowledgeType knowledgeType) {
        hibernateTemplate.save(knowledgeType);
    }

    @Transactional(readOnly=true)
    @SuppressWarnings("unchecked")
    public List<KnowledgeType> findAll() {
        return (List<KnowledgeType>)hibernateTemplate.find("from " + KnowledgeType.class.getName());
    }

    public void updateKnowledgeType(KnowledgeType knowledgeType) {
        hibernateTemplate.update(knowledgeType);
    }

    public void removeKnowledgeType(final int knowledgeTypeId) {
        hibernateTemplate.execute(new HibernateCallback<Object>() {

            @Override
            public Knowledge doInHibernate(Session ses) throws HibernateException, SQLException {
                Query q = ses.createQuery("DELETE FROM KnowledgeType WHERE id= :knowledgeTypeId");
                q.setInteger("knowledgeTypeId", knowledgeTypeId);
                q.executeUpdate();
                return null;
            }
        });
    }
}
