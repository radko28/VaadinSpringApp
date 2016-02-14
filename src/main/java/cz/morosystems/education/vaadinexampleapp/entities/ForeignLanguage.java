package cz.morosystems.education.vaadinexampleapp.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 * 
 * Entity for foreign language.
 * 
 * @author radoslav.kuzma
 * 
 */
@Entity
@Table(name = "FOREING_LANGUAGE")
public class ForeignLanguage implements Serializable {


    /**
     * 
     */
    private static final long serialVersionUID = 1549224565945852931L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "uuid", nullable = false, columnDefinition = "varchar(32)")
    private String id;
    @Enumerated(EnumType.STRING)
    @Column(name = "language", nullable = true, columnDefinition = "varchar(20)")
    private ForeingLanguageType language;
    @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = true, columnDefinition = "varchar(20)")
    private LanguageLevelType level;


    @Column(nullable = false)
    private String description;

    @Column(name = "created", nullable = false)
    @Type(type = "jodaDateTime")
    private DateTime created;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User users;


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public LanguageLevelType getLevel() {
        return level;
    }


    public void setLevel(LanguageLevelType level) {
        this.level = level;
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
