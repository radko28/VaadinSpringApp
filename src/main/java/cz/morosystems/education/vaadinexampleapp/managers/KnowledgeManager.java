package cz.morosystems.education.vaadinexampleapp.managers;

import java.util.List;

import cz.morosystems.education.vaadinexampleapp.entities.KnowledgeType;


/**
 * 
 * @author andrej.klima
 *
 */
public interface KnowledgeManager {
    public void save(KnowledgeType knowledgeType);
    public List<KnowledgeType> findAll();
    public void update(KnowledgeType knowledgeType);
    public void remove(int knowledgeTypeId);
}
