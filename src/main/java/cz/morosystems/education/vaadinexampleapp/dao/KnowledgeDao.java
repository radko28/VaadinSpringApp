package cz.morosystems.education.vaadinexampleapp.dao;

import java.util.List;

import cz.morosystems.education.vaadinexampleapp.entities.Knowledge;
import cz.morosystems.education.vaadinexampleapp.entities.KnowledgeType;

/**
 * An interface of a class for adding, updating or removing user's knowledge.
 * 
 * @author andrej.klima
 * 
 */
public interface KnowledgeDao {

    /**
     * Save knowledge entity
     */
    public void save(Knowledge knowledge);
    /**
     * Update knowledge entity
     */
    public void update(Knowledge knowledge);
    /**
     * Remove knowledge entity
     */
    public void remove(String knowledgeId);

    public void saveKnowledgeType(KnowledgeType knowledgeType);
    public List<KnowledgeType> findAll();
    public void updateKnowledgeType(KnowledgeType knowledgeType);
    public void removeKnowledgeType(int knowledgeTypeId);

}
