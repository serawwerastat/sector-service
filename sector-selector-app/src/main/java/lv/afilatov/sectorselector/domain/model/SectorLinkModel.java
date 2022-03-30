package lv.afilatov.sectorselector.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sector_to_sector")
public class SectorLinkModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "parent_id")
    private int parentId;
    @Column(name = "child_id")
    private int childId;

    public int getId() {
        return id;
    }

    public SectorLinkModel setId(int id) {
        this.id = id;
        return this;
    }

    public int getParentId() {
        return parentId;
    }

    public SectorLinkModel setParentId(int parentId) {
        this.parentId = parentId;
        return this;
    }

    public int getChildId() {
        return childId;
    }

    public SectorLinkModel setChildId(int childId) {
        this.childId = childId;
        return this;
    }
}
