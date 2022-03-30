package lv.afilatov.sectorselector.test_util.sector;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import lv.afilatov.sectorselector.domain.model.SectorModel;
import lv.afilatov.sectorselector.domain.response.SectorResponse;

public class TestSectorAssertions {

    public static void fieldsAreEqual(SectorResponse response, SectorModel sector, int level) {
        assertThat(response.getId(), is(String.valueOf(sector.getId())));
        assertThat(response.getValue(), is(sector.getValue()));
        assertThat(response.isParent(), is(not(sector.getChildSectors().isEmpty())));
        assertThat(response.getLevel(), is(level));
    }
}
