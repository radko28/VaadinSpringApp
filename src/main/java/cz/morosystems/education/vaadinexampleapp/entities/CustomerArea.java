package cz.morosystems.education.vaadinexampleapp.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 * 
 * @author radoslav.kuzma
 * 
 */
@Entity
@Table(name = "CustomerArea")
public class CustomerArea implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "uuid", nullable = false, columnDefinition = "varchar(32)")
    private String id;


    @Column(name = "name", nullable = false, columnDefinition = "varchar(32)")
    private String name;

    @Column(name = "description", nullable = true, columnDefinition = "varchar(255)")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "customerAreaType", nullable = false, columnDefinition = "varchar(32)")
    private CustomerAreaType customerAreaType;

    @ManyToMany(mappedBy = "customerArea")
    // @JoinTable(name = "PROJECT_CUSTOMER_MAP", joinColumns = { @JoinColumn(name = "project_id") }, inverseJoinColumns = { @JoinColumn(name
    // = "customer_area_id") })
    private List<Project> projects;

    @Column(name = "created", nullable = false)
    @Type(type = "jodaDateTime")
    private DateTime created;


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public CustomerAreaType getCustomerAreaType() {
        return customerAreaType;
    }


    public void setCustomerAreaType(CustomerAreaType customerAreaType) {
        this.customerAreaType = customerAreaType;
    }


    public List<Project> getProjects() {
        return projects;
    }


    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }


    public DateTime getCreated() {
        return created;
    }


    public void setCreated(DateTime created) {
        this.created = created;
    }


}
