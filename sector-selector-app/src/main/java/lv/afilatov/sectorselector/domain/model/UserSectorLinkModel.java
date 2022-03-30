package lv.afilatov.sectorselector.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_to_sector")
public class UserSectorLinkModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "sector_id")
    private int sectorId;

    public UserSectorLinkModel() {
    }

    public UserSectorLinkModel(int userId, int sectorId) {
        this.userId = userId;
        this.sectorId = sectorId;
    }

    public int getId() {
        return id;
    }

    public UserSectorLinkModel setId(int id) {
        this.id = id;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public UserSectorLinkModel setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getSectorId() {
        return sectorId;
    }

    public UserSectorLinkModel setSectorId(int sectorId) {
        this.sectorId = sectorId;
        return this;
    }
}
