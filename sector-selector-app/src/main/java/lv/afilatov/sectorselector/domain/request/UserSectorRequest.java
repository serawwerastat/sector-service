package lv.afilatov.sectorselector.domain.request;

import java.util.Set;

public class UserSectorRequest {

    private String name;
    private Set<Integer> sectors;
    private boolean agreementAccepted;

    public String getName() {
        return name;
    }

    public UserSectorRequest setName(String name) {
        this.name = name;
        return this;
    }

    public Set<Integer> getSectors() {
        return sectors;
    }

    public UserSectorRequest setSectors(Set<Integer> sectors) {
        this.sectors = sectors;
        return this;
    }

    public boolean isAgreementAccepted() {
        return agreementAccepted;
    }

    public UserSectorRequest setAgreementAccepted(boolean agreementAccepted) {
        this.agreementAccepted = agreementAccepted;
        return this;
    }

    public UserSectorRequest addSector(Integer sector) {
        this.sectors.add(sector);
        return this;
    }

    public UserSectorRequest removeSector(Integer sector) {
        this.sectors.remove(sector);
        return this;
    }

    @Override
    public String toString() {
        return "UserSectorRequest{" +
                "name='" + name + '\'' +
                ", sectors=" + sectors +
                ", agreementAccepted=" + agreementAccepted +
                '}';
    }
}
