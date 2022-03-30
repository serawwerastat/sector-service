package lv.afilatov.sectorselector.domain.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import lv.afilatov.sectorselector.domain.request.UserSectorRequest;

@Entity
@Table(name = "user_sector")
public class UserModel {

    @Transient
    private final Set<Integer> sectorIds = new HashSet<>();
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "is_agreement_accepted")
    private boolean agreementAccepted;

    public static UserModel from(UserSectorRequest request) {
        return new UserModel()
                .setName(request.getName())
                .setAgreementAccepted(request.isAgreementAccepted());
    }

    public int getId() {
        return id;
    }

    public UserModel setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserModel setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isAgreementAccepted() {
        return agreementAccepted;
    }

    public UserModel setAgreementAccepted(boolean agreementAccepted) {
        this.agreementAccepted = agreementAccepted;
        return this;
    }

    public Set<Integer> getSectorIds() {
        return sectorIds;
    }

    public UserModel addSectorId(Integer sectorId) {
        this.sectorIds.add(sectorId);
        return this;
    }

    public UserModel addSectorIds(Set<Integer> sectorIds) {
        this.sectorIds.addAll(sectorIds);
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

        UserModel userModel = (UserModel) o;

        return new EqualsBuilder()
                .append(id, userModel.id)
                .append(agreementAccepted, userModel.agreementAccepted)
                .append(name, userModel.name)
                .append(sectorIds, userModel.sectorIds)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(agreementAccepted)
                .append(sectorIds)
                .toHashCode();
    }
}
