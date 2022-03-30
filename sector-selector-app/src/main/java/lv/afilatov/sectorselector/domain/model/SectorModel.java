package lv.afilatov.sectorselector.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "sector")
public class SectorModel {

    @Transient
    private final List<SectorModel> childSectors = new ArrayList<>();
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "value")
    private String value;

    public int getId() {
        return id;
    }

    public SectorModel setId(int id) {
        this.id = id;
        return this;
    }

    public String getValue() {
        return value;
    }

    public SectorModel setValue(String value) {
        this.value = value;
        return this;
    }

    public List<SectorModel> getChildSectors() {
        return childSectors;
    }

    public SectorModel addChildSector(SectorModel childSector) {
        this.childSectors.add(childSector);
        return this;
    }

    public SectorModel addChildSectors(List<SectorModel> childSectors) {
        this.childSectors.addAll(childSectors);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        var that = (SectorModel) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(value, that.value)
                .append(childSectors, that.childSectors)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(value)
                .append(childSectors)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "SectorModel{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }
}
