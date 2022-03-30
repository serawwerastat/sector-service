package lv.afilatov.sectorselector.test_util.sector;

import static lv.afilatov.sectorselector.domain.response.SectorResponse.fromSectorModel;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.util.Random;

import lv.afilatov.sectorselector.domain.model.SectorLinkModel;
import lv.afilatov.sectorselector.domain.model.SectorModel;
import lv.afilatov.sectorselector.domain.response.SectorResponse;

public class TestSectorConstructors {

    public static SectorModel createSectorModel() {
        var id = new Random().nextInt();
        return new SectorModel()
                .setId(id)
                .setValue("sector-value-" + randomAlphabetic(4));
    }

    public static SectorLinkModel createSectorLinkModel() {
        var parentId = new Random().nextInt();
        var childId = new Random().nextInt();

        return createSectorLinkModel(parentId, childId);
    }

    public static SectorLinkModel createSectorLinkModel(int parentId, int childId) {
        var id = new Random().nextInt();
        return new SectorLinkModel()
                .setId(id)
                .setParentId(parentId)
                .setChildId(childId);
    }

    public static SectorResponse createSectorResponse(SectorModel sectorModel, int level) {
        return fromSectorModel(sectorModel)
                .setParent(!sectorModel.getChildSectors().isEmpty())
                .setLevel(level);
    }
}
