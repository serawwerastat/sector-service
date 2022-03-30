package lv.afilatov.sectorselector.service.sector;

import static lv.afilatov.sectorselector.test_util.sector.TestSectorConstructors.createSectorModel;
import static lv.afilatov.sectorselector.test_util.sector.TestSectorConstructors.createSectorResponse;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SectorServiceTest {

    private final GetSectorService getSectorService = mock(GetSectorService.class);
    private final ConvertSectorService convertSectorService = mock(ConvertSectorService.class);

    private final SectorService sectorService = new SectorService(getSectorService, convertSectorService);

    @Test
    public void getAllSectorsTest() {
        var sector1 = createSectorModel();
        var sector2 = createSectorModel();
        var sectors = List.of(sector1, sector2);
        when(getSectorService.getAllSectorModels()).thenReturn(sectors);

        var response1 = createSectorResponse(sector1, 0);
        var response2 = createSectorResponse(sector2, 0);
        var mockResponses = List.of(response1, response2);
        when(convertSectorService.convertSectorModelsToSectorResponses(sectors)).thenReturn(mockResponses);

        var responses = sectorService.getAllSectors();
        assertThat(responses, is(mockResponses));
    }
}
