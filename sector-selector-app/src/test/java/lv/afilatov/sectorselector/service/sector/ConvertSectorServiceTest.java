package lv.afilatov.sectorselector.service.sector;

import static java.util.Collections.emptyList;
import static lv.afilatov.sectorselector.test_util.sector.TestSectorAssertions.fieldsAreEqual;
import static lv.afilatov.sectorselector.test_util.sector.TestSectorConstructors.createSectorModel;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import javax.ws.rs.InternalServerErrorException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ConvertSectorServiceTest {

    private final ConvertSectorService convertSectorService = new ConvertSectorService();

    @Test
    public void convertSectorModelsToSectorResponsesTest() {
        var sector1 = createSectorModel();
        var sector2 = createSectorModel();
        var sector3 = createSectorModel();
        var sector4 = createSectorModel();

        sector2.addChildSector(sector3);
        sector1.addChildSector(sector2);

        var sectors = List.of(sector1, sector4);

        var responses = convertSectorService.convertSectorModelsToSectorResponses(sectors);
        assertThat(responses.size(), is(4));
        fieldsAreEqual(responses.get(0), sector1, 0);
        fieldsAreEqual(responses.get(1), sector2, 1);
        fieldsAreEqual(responses.get(2), sector3, 2);
        fieldsAreEqual(responses.get(3), sector4, 0);
    }

    @Test
    public void convertSectorModelsToSectorResponsesEmptySectorsTest() {
        var responses = convertSectorService.convertSectorModelsToSectorResponses(emptyList());
        assertThat(responses, is(emptyList()));
    }

    @Test
    public void convertSectorModelsToSectorResponsesNullSectorTest() {
        assertThrows(InternalServerErrorException.class, () -> convertSectorService.convertSectorModelsToSectorResponses(null));
    }
}
