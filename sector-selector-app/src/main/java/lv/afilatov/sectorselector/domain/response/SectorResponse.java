package lv.afilatov.sectorselector.domain.response;

import lv.afilatov.sectorselector.domain.model.SectorModel;

public class SectorResponse {

    private String id;
    private String value;
    private boolean parent;
    private int level;

    public static SectorResponse fromSectorModel(SectorModel sectorModel) {
        return new SectorResponse()
                .setId(String.valueOf(sectorModel.getId()))
                .setValue(sectorModel.getValue());
    }

    public String getId() {
        return id;
    }

    public SectorResponse setId(String id) {
        this.id = id;
        return this;
    }

    public String getValue() {
        return value;
    }

    public SectorResponse setValue(String value) {
        this.value = value;
        return this;
    }

    public boolean isParent() {
        return parent;
    }

    public SectorResponse setParent(boolean parent) {
        this.parent = parent;
        return this;
    }

    public int getLevel() {
        return level;
    }

    public SectorResponse setLevel(int level) {
        this.level = level;
        return this;
    }
}
