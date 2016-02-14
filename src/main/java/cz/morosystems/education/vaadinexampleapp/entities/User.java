package cz.morosystems.education.vaadinexampleapp.entities;


import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.joda.time.DateTime;
import org.joda.time.contrib.hibernate.PersistentDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import cz.morosystems.education.vaadinexampleapp.util.AppHelper;

/**
 * 
 * User entity defines information about user.
 * 
 * @author radoslav.kuzma
 * 
 */
@Entity
@Table(name = "USERS")
@TypeDefs({ @TypeDef(name = "jodaDateTime", typeClass = PersistentDateTime.class) })
public class User implements Serializable {

    private static final long serialVersionUID = 7379127887159694223L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "uuid", nullable = false, columnDefinition = "varchar(32)")
    private String userId = "";

    @Size(min = 1, max = 15, message = "{field.size}")
    @Column(name = "username", nullable = false, length = 15)
    private String username = "";

    @Size(min = 1, max = 15, message = "{field.size}")
    @Column(name = "firstname", nullable = false, length = 15)
    private String firstname = "";

    @Column(nullable = false)
    @Type(type = "jodaDateTime")
    @DateTimeFormat(pattern = "d.M.yyyy")
    private DateTime birthdate;

    @Size(min = 1, max = 15, message = "{field.size}")
    @Column(name = "lastname", nullable = false, length = 15)
    private String lastname = "";

    @Size(min = 1, message = "{field.required}")
    @Column(name = "password", nullable = false)
    private String password = "";

    @Size(min = 3, max = 100, message = "{field.size}")
    @Column(name = "email", nullable = false, length = 100)
    private String email = "";

    @OneToMany(mappedBy = "users")
    private Set<Device> devices;

    @OneToOne(mappedBy = "users")
    private Authority authorities = new Authority();

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "created", nullable = false)
    @Type(type = "jodaDateTime")
    private DateTime created;

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "photo", nullable = true)
    @Lob
    private byte[] photo;

    @OneToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private Set<Knowledge> knowledge;

    @OneToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private Set<ForeignLanguage> foreignLanguages;

    @OneToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private Set<Project> projects;

    @Transient
    private String oldpassword;

    @Transient
    private String confirm = "";

    @Transient
    private Date birthdateDate;

    @Transient
    private boolean adminRole;

    public DateTime getCreated() {
        return created;
    }

    public void setCreated(DateTime created) {
        this.created = created;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Set<Device> getDevices() {
        return devices;
    }

    public int getSizeOfDevices() {
        return devices.size();
    }

    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public DateTime getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(DateTime birthdate) {
        this.birthdate = birthdate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Authority getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Authority authorities) {
        this.authorities = authorities;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOldpassword() {
        return oldpassword;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }

    public boolean getRole() {
        return adminRole;
    }
    public boolean getAdminRole() {
        if (null == authorities) {
            return false;
        }

        return authorities.getAuthority() == RoleType.ROLE_ADMIN;
    }

    public Date getBirthdateDate() {
        if (null != birthdate) {
            birthdateDate = this.birthdate.toDate();
        }

        return birthdateDate;
    }

    public String getBirthdateString() {
        if (birthdate != null)
            return org.joda.time.format.DateTimeFormat.forPattern(AppHelper.DATE_FORMAT).print(birthdate);

        return "";
    }

    public void setBirthdateDate(Date birthdateDate) {
        this.birthdateDate = birthdateDate;
    }

    public void setAdminRole(boolean adminRole) {
        this.adminRole = adminRole;
    }

    public Set<Knowledge> getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(Set<Knowledge> knowledge) {
        this.knowledge = knowledge;
    }

    public String toString() {
        return "User [" + userId + "," + firstname + "," + lastname + "," + birthdate + "," + username + "," + password + "," + created
                + "]";
    }


    public byte[] getPhoto() {
        return photo;
    }


    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }


    public Set<ForeignLanguage> getForeignLanguages() {
        return foreignLanguages;
    }


    public void setForeignLanguages(Set<ForeignLanguage> foreignLanguages) {
        this.foreignLanguages = foreignLanguages;
    }


    public Set<Project> getProjects() {
        return projects;
    }


    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }


}
