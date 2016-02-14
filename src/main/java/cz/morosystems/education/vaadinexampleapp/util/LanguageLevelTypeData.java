package cz.morosystems.education.vaadinexampleapp.util;

import cz.morosystems.education.vaadinexampleapp.entities.LanguageLevelType;


public class LanguageLevelTypeData {

    private String description;
    private LanguageLevelType level;

    public LanguageLevelTypeData(String description, LanguageLevelType level) {
        this.description = description;
        this.level = level;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public LanguageLevelType getLevel() {
        return level;
    }


    public void setLevel(LanguageLevelType level) {
        this.level = level;
    }

}
