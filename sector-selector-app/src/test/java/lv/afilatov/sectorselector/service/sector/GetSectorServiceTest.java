package lv.afilatov.sectorselector.service.sector;

import static java.util.Collections.emptyList;
import static lv.afilatov.sectorselector.test_util.sector.TestSectorConstructors.createSectorLinkModel;
import static lv.afilatov.sectorselector.test_util.sector.TestSectorConstructors.createSectorModel;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Random;

import javax.ws.rs.InternalServerErrorException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import lv.afilatov.sectorselector.repository.SectorLinkRepository;
import lv.afilatov.sectorselector.repository.SectorRepository;

@ExtendWith(MockitoExtension.class)
public class GetSectorServiceTest {

    private final SectorLinkRepository sectorLinkRepository = mock(SectorLinkRepository.class);
    private final SectorRepository sectorRepository = mock(SectorRepository.class);

    private final GetSectorService getSectorService = new GetSectorService(sectorLinkRepository, sectorRepository);

    /**
     * Structure:
     * sector1
     * sector2
     * sector3
     * sector4
     */
    @Test
    public void getAllSectorModelsTest() {
        var sector1 = createSectorModel();
        var sector2 = createSectorModel();
        var sector3 = createSectorModel();
        var sector4 = createSectorModel();

        var sectorLink1 = createSectorLinkModel(sector1.getId(), sector2.getId());
        var sectorLink2 = createSectorLinkModel(sector2.getId(), sector3.getId());

        var storedSectorLinks = List.of(sectorLink1, sectorLink2);
        when(sectorLinkRepository.findAll()).thenReturn(storedSectorLinks);
        var storedSectors = List.of(sector1, sector2, sector3, sector4);
        when(sectorRepository.findAll()).thenReturn(storedSectors);
        var sectors = getSectorService.getAllSectorModels();
        assertThat(sectors.size(), is(2));
        assertThat(sectors.get(0), is(sector1));
        assertThat(sectors.get(0).getChildSectors().size(), is(1));
        assertThat(sectors.get(0).getChildSectors().get(0), is(sector2));
        assertThat(sectors.get(0).getChildSectors().get(0).getChildSectors().size(), is(1));
        assertThat(sectors.get(0).getChildSectors().get(0).getChildSectors().get(0), is(sector3));
        assertThat(sectors.get(1), is(sector4));
    }

    /**
     * Structure:
     * sector1
     * sector2
     * sector3
     * sector4
     */
    @Test
    public void getAllSectorModelsNoLinksTest() {
        var sector1 = createSectorModel();
        var sector2 = createSectorModel();
        var sector3 = createSectorModel();
        var sector4 = createSectorModel();

        when(sectorLinkRepository.findAll()).thenReturn(emptyList());
        var storedSectors = List.of(sector1, sector2, sector3, sector4);
        when(sectorRepository.findAll()).thenReturn(storedSectors);
        var sectors = getSectorService.getAllSectorModels();
        assertThat(sectors.size(), is(4));
        assertThat(sectors, is(storedSectors));
    }

    @Test
    public void getAllSectorModelsNoSectorsTest() {
        var sectorLink1 = createSectorLinkModel();
        var sectorLink2 = createSectorLinkModel();

        var storedSectorLinks = List.of(sectorLink1, sectorLink2);
        when(sectorLinkRepository.findAll()).thenReturn(storedSectorLinks);
        when(sectorRepository.findAll()).thenReturn(emptyList());
        assertThrows(InternalServerErrorException.class, getSectorService::getAllSectorModels);
    }

    @Test
    public void getAllSectorModelsNoSectorsAndNoLinksTest() {
        when(sectorLinkRepository.findAll()).thenReturn(emptyList());
        when(sectorRepository.findAll()).thenReturn(emptyList());
        var sectors = getSectorService.getAllSectorModels();
        assertThat(sectors, is(emptyList()));
    }

    @Test
    public void getAllSectorModelsNotExistingChildTest() {
        var sector1 = createSectorModel();
        var sector2 = createSectorModel();
        var sector3 = createSectorModel();
        var sector4 = createSectorModel();

        var notExistingSectorId = new Random().nextInt();
        var sectorLink1 = createSectorLinkModel(sector1.getId(), notExistingSectorId);
        var sectorLink2 = createSectorLinkModel(sector2.getId(), sector3.getId());

        var storedSectorLinks = List.of(sectorLink1, sectorLink2);
        when(sectorLinkRepository.findAll()).thenReturn(storedSectorLinks);
        var storedSectors = List.of(sector1, sector2, sector3, sector4);
        when(sectorRepository.findAll()).thenReturn(storedSectors);
        assertThrows(InternalServerErrorException.class, getSectorService::getAllSectorModels);
    }

    @Test
    public void getAllSectorModelsNotExistingParentTest() {
        var sector1 = createSectorModel();
        var sector2 = createSectorModel();
        var sector3 = createSectorModel();
        var sector4 = createSectorModel();

        var notExistingSectorId = new Random().nextInt();
        var sectorLink1 = createSectorLinkModel(notExistingSectorId, sector2.getId());
        var sectorLink2 = createSectorLinkModel(sector2.getId(), sector3.getId());

        var storedSectorLinks = List.of(sectorLink1, sectorLink2);
        when(sectorLinkRepository.findAll()).thenReturn(storedSectorLinks);
        var storedSectors = List.of(sector1, sector2, sector3, sector4);
        when(sectorRepository.findAll()).thenReturn(storedSectors);
        assertThrows(InternalServerErrorException.class, getSectorService::getAllSectorModels);
    }
}
