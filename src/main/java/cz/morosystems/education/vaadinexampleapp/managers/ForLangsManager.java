package cz.morosystems.education.vaadinexampleapp.managers;

import java.util.List;

import cz.morosystems.education.vaadinexampleapp.entities.ForeignLanguage;

/**
 * 
 * Logic interface for Foreign language.
 * 
 * @author radoslav.kuzma
 * 
 */
public interface ForLangsManager {

    /**
     * find language.
     * 
     */
    public ForeignLanguage findForeignLanguageById(String id);
    /**
     * 
     * find list of languages for user.
     * 
     */
    public List<ForeignLanguage> findForeignLanguagesByUserName(String username);
    /**
     * 
     * delete language.
     * 
     * 
     */
    public void deleteForeignLanguage(String id);
    /**
     * 
     * create new language.
     * 
     */
    public void save(ForeignLanguage foreingLanguage, String username);
    /**
     * 
     * update existing language.
     * 
     */
    public void update(ForeignLanguage foreingLanguage, String username);

}
