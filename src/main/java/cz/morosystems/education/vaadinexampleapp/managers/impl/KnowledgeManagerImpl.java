package cz.morosystems.education.vaadinexampleapp.managers.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cz.morosystems.education.vaadinexampleapp.dao.KnowledgeDao;
import cz.morosystems.education.vaadinexampleapp.entities.KnowledgeType;
import cz.morosystems.education.vaadinexampleapp.managers.KnowledgeManager;

@Component
@Scope("prototype")
public class KnowledgeManagerImpl implements KnowledgeManager {

    /** Spring component scan injects KnowledgeDao bean */
    @Autowired
    KnowledgeDao knowledgeDao;

    @Override
    public List<KnowledgeType> findAll() {
        return knowledgeDao.findAll();
    }

    @Override
    public void save(KnowledgeType knowledgeType) {
        knowledgeDao.saveKnowledgeType(knowledgeType);
    }

    @Override
    public void update(KnowledgeType knowledgeType) {
        knowledgeDao.updateKnowledgeType(knowledgeType);
    }

    @Override
    public void remove(int knowledgeTypeId) {
       knowledgeDao.removeKnowledgeType(knowledgeTypeId);
    }

}
