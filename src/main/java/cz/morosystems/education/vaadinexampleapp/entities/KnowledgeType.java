package cz.morosystems.education.vaadinexampleapp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

/**
 * Type of user knowledge
 * 
 * @author andrej.klima
 * 
 */
@Entity
@Table
public class KnowledgeType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "type", nullable = false, columnDefinition = "varchar(32)")
    private String type;

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
