package cz.morosystems.education.vaadinexampleapp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

/**
 * User's knowledge
 * @author andrej.klima
 *
 */
@Entity
public class Knowledge {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "uuid", nullable = false, columnDefinition = "varchar(32)")
    private String knowledgeId = "";

    @Column(nullable=false)
    private String type;

    @Column(nullable=true)
    private String detail;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User users;

    public String getKnowledgeId() {
        return knowledgeId;
    }

    public void setKnowledgeId(String knowledgeId) {
        this.knowledgeId = knowledgeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public String toString() {
        return "Knowledge["+type+","+detail+"]";
    }
}
