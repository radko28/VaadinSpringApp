package cz.morosystems.education.vaadinexampleapp.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 * 
 * @author radoslav.kuzma
 * 
 */
@Entity
@Table(name = "Project")
public class Project implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6791469237133624999L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "uuid", nullable = false, columnDefinition = "varchar(32)")
    private String id;

    @Column(name = "description", nullable = true, columnDefinition = "varchar(255)")
    private String description;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(32)")
    private String name;

    @Column(name = "created", nullable = false)
    @Type(type = "jodaDateTime")
    private DateTime created;

    @Column(name = "fromDate", nullable = false)
    @Type(type = "jodaDateTime")
    private DateTime fromDate;

    @Column(name = "toDate", nullable = false)
    @Type(type = "jodaDateTime")
    private DateTime toDate;

    @Column(name = "technology", nullable = false, columnDefinition = "varchar(32)")
    private String technology;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User users;

    @ManyToMany
    @Cascade(CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "PROJECT_ROLE_MAP", joinColumns = { @JoinColumn(name = "project_id") }, inverseJoinColumns = { @JoinColumn(name = "project_role_id") })
    private List<ProjectRole> projectRoles = new ArrayList<ProjectRole>();

    @ManyToMany
    @Cascade(CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "PROJECT_CUSTOMER_MAP", joinColumns = { @JoinColumn(name = "project_id") }, inverseJoinColumns = { @JoinColumn(name = "customer_area_id") })
    private List<CustomerArea> customerArea = new ArrayList<CustomerArea>();

    @Column(name = "company", nullable = false, columnDefinition = "varchar(32)")
    private String company;

    @Transient
    private ProjectRoleType projectRoleType;
    @Transient
    private CustomerAreaType customerAreaType;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(DateTime fromDate) {
        this.fromDate = fromDate;
    }

    public DateTime getToDate() {
        return toDate;
    }

    public void setToDate(DateTime toDate) {
        this.toDate = toDate;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }


    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public DateTime getCreated() {
        return created;
    }


    public void setCreated(DateTime created) {
        this.created = created;
    }


    public User getUsers() {
        return users;
    }


    public void setUsers(User users) {
        this.users = users;
    }


    public List<ProjectRole> getProjectRoles() {
        return projectRoles;
    }


    public void setProjectRoles(List<ProjectRole> projectRoles) {
        this.projectRoles = projectRoles;
    }


    public List<CustomerArea> getCustomerArea() {
        return customerArea;
    }


    public void setCustomerArea(List<CustomerArea> customerArea) {
        this.customerArea = customerArea;
    }


    public ProjectRoleType getProjectRoleTypeView() {
        ProjectRoleType result = null;
        if (projectRoles != null && projectRoles.size() != 0) {
            result = projectRoles.get(0).getProjectRoleType();
        } else {
            result = projectRoleType;
        }

        return result;
    }

    public ProjectRoleType getProjectRoleType() {
        return projectRoleType;
    }


    public void setProjectRoleType(ProjectRoleType projectRoleType) {
        this.projectRoleType = projectRoleType;
    }


    public CustomerAreaType getCustomerAreaTypeView() {
        CustomerAreaType result = null;
        if (customerArea != null && customerArea.size() != 0) {
            result = customerArea.get(0).getCustomerAreaType();
        } else {
            result = customerAreaType;
        }
        return result;
    }

    public CustomerAreaType getCustomerAreaType() {
        return customerAreaType;
    }


    public void setCustomerAreaType(CustomerAreaType customerAreaType) {
        this.customerAreaType = customerAreaType;
    }


}
