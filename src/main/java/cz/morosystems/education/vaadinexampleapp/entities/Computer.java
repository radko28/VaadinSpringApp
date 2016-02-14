package cz.morosystems.education.vaadinexampleapp.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

/**
 * 
 * Computer entity is type of entity device.
 * 
 * @author radoslav.kuzma
 * 
 */
@Entity
@DiscriminatorValue("COMPUTER")
public class Computer extends Device {

    @Enumerated(EnumType.STRING)
    @Column(name = "deviceType", nullable = true, columnDefinition = "varchar(20)")
    private ComputerType computerType;

    @Transient
    private String computerTypeString;

    public ComputerType getComputerType() {
        return computerType;
    }

    public void setComputerType(ComputerType computerType) {
        this.computerType = computerType;
    }


    public String getComputerTypeString() {
        return computerTypeString;
    }


    public void setComputerTypeString(String computerTypeString) {
        this.computerTypeString = computerTypeString;
    }
}
