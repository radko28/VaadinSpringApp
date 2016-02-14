package cz.morosystems.education.vaadinexampleapp.util;

import cz.morosystems.education.vaadinexampleapp.entities.ComputerType;

/**
 * Data type for ComputerForm combobox
 * 
 * @author radoslav.kuzma
 * 
 */
public class ComputerData {

    private String description;
    private ComputerType computerType;

    public ComputerData(String description, ComputerType computerType) {
        this.description = description;
        this.computerType = computerType;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ComputerType getComputerType() {
        return computerType;
    }

    public void setComputerType(ComputerType computerType) {
        this.computerType = computerType;
    }

}
