package vars.knowledgebase.jpa;

import com.google.gson.annotations.SerializedName;
import vars.gson.Exclude;
import vars.jpa.JPAEntity;
import vars.knowledgebase.Concept;
import vars.knowledgebase.ConceptName;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Brian Schlining
 * @since 2018-02-14T19:08:00
 */
public class SimpleConcept implements Serializable, JPAEntity {

    @SerializedName("children")
    @OneToMany(
            targetEntity = SimpleConcept.class,
            mappedBy = "parentConcept",
            fetch = FetchType.LAZY,
            cascade = { CascadeType.ALL }
    )
    private List<SimpleConcept> childConcepts;

    @SerializedName("names")
    @OneToMany(
            targetEntity = ConceptNameImpl.class,
            mappedBy = "concept",
            fetch = FetchType.EAGER,
            cascade = { CascadeType.ALL }
    )
    private List<ConceptNameImpl> conceptNames;

    @Exclude
    @Id
    @Column(
            name = "id",
            nullable = false,
            updatable = false
    )
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Concept_Gen")
    @TableGenerator(
            name = "Concept_Gen",
            table = "UniqueID",
            pkColumnName = "TableName",
            valueColumnName = "NextID",
            pkColumnValue = "Concept",
            allocationSize = 1
    )
    private Long id;

    @Exclude
    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = true,
            targetEntity = ConceptImpl.class
    )
    @JoinColumn(name = "ParentConceptID_FK")
    private Concept parentConcept;

    @Column(name = "RankLevel", length = 20)
    private String rankLevel;

    @Column(name = "RankName", length = 20)
    private String rankName;

    @Exclude
    @Version
    @Column(name = "LAST_UPDATED_TIME")
    private Timestamp updatedTime;

    public List<SimpleConcept> getChildConcepts() {
        if (childConcepts == null) {
            childConcepts = new ArrayList<>();
        }

        return childConcepts;
    }

    public ConceptName getPrimaryConceptName() {
        conceptNames.stream()
                .filter(sc)
    }

    public List<SimpleConceptName> getConceptNames() {
        if (conceptNames == null) {
            conceptNames = new ArrayList<>();
        }
        return conceptNames;
    }
}
