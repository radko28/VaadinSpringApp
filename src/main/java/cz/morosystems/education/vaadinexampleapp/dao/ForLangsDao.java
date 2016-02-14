package cz.morosystems.education.vaadinexampleapp.dao;

import java.util.List;

import cz.morosystems.education.vaadinexampleapp.entities.ForeignLanguage;
import cz.morosystems.education.vaadinexampleapp.entities.User;

/**
 * Dao for foreign language
 * 
 * @author radoslav.kuzma
 * 
 */
public interface ForLangsDao {

    /**
     * 
     * Read list of language for user.
     * 
     * 
     */
    public List<ForeignLanguage> findForeignLanguagesByUser(User user);
    /**
     * 
     * Delete language.
     * 
     */
    public void remove(ForeignLanguage foreignLanguage);
    /**
     * 
     * Get language by id.
     * 
     */
    public ForeignLanguage findForeignLanguageById(String id);
    /**
     * 
     * Create new language.
     * 
     */
    public void save(ForeignLanguage foreingLanguage);
    /**
     * Update language.
     * 
     */
    public void update(ForeignLanguage foreingLanguage);


}
