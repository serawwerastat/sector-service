package lv.afilatov.sectorselector.domain.response;

import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class UserSectorResponse {

    private int userId;
    private String name;
    private Set<String> sectors;
    private boolean agreementAccepted;

    public UserSectorResponse() {
    }

    public int getUserId() {
        return userId;
    }

    public UserSectorResponse setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserSectorResponse setName(String name) {
        this.name = name;
        return this;
    }

    public Set<String> getSectors() {
        return sectors;
    }

    public UserSectorResponse setSectors(Set<String> sectors) {
        this.sectors = sectors;
        return this;
    }

    public boolean isAgreementAccepted() {
        return agreementAccepted;
    }

    public UserSectorResponse setAgreementAccepted(boolean agreementAccepted) {
        this.agreementAccepted = agreementAccepted;
        return this;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserSectorResponse response = (UserSectorResponse) o;

        return new EqualsBuilder()
                .append(userId, response.userId)
                .append(agreementAccepted, response.agreementAccepted)
                .append(name, response.name)
                .append(sectors, response.sectors)
                .isEquals();
    }

    @Override public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(userId)
                .append(name)
                .append(sectors)
                .append(agreementAccepted)
                .toHashCode();
    }
}
