package cz.morosystems.education.vaadinexampleapp.util;

import cz.morosystems.education.vaadinexampleapp.entities.ForeingLanguageType;


public class ForeingLanguageTypeData {

    private String description;
    private ForeingLanguageType language;

    public ForeingLanguageTypeData(String description, ForeingLanguageType language) {
        this.description = description;
        this.language = language;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public ForeingLanguageType getLanguage() {
        return language;
    }


    public void setLanguage(ForeingLanguageType language) {
        this.language = language;
    }

}
