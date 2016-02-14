package cz.morosystems.education.vaadinexampleapp.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 
 * Mobil phone entity is type of entity device.
 * 
 * @author radoslav.kuzma
 * 
 */
@Entity
@DiscriminatorValue("CELL_PHONE")
public class CellPhone extends Device {

    @Column(name = "phoneNumber", nullable = true, columnDefinition = "varchar(15)")
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
