package lv.afilatov.sectorselector.service.userSector;

import static lv.afilatov.sectorselector.test_util.sector.TestSectorConstructors.createSectorModel;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.ws.rs.BadRequestException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import lv.afilatov.sectorselector.repository.SectorRepository;
import lv.afilatov.sectorselector.repository.UserRepository;
import lv.afilatov.sectorselector.repository.UserSectorLinkRepository;

@ExtendWith(MockitoExtension.class)
public class CoreUserSectorServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final SectorRepository sectorRepository = mock(SectorRepository.class);
    private final UserSectorLinkRepository userSectorLinkRepository = mock(UserSectorLinkRepository.class);

    private final TestCoreUserSectorServiceTest coreUserSectorServiceTest =
            new TestCoreUserSectorServiceTest(
                    userRepository,
                    sectorRepository,
                    userSectorLinkRepository
            );

    @Test
    public void createUserSectorLinkModelsTest() {
        var userId = new Random().nextInt();

        var sector1 = createSectorModel();
        var sector2 = createSectorModel();
        var sectors = List.of(sector1, sector2);

        var sectorId1 = sector1.getId();
        var sectorId2 = sector2.getId();
        var sectorIds = Set.of(sectorId1, sectorId2);

        var userSectorLinks = coreUserSectorServiceTest.createUserSectorLinkModels(sectorIds, userId, sectors);
        assertThat(userSectorLinks.size(), is(2));
        assertThat(userSectorLinks.get(0).getSectorId(), is(sectorId1));
        assertThat(userSectorLinks.get(0).getUserId(), is(userId));
        assertThat(userSectorLinks.get(1).getSectorId(), is(sectorId2));
        assertThat(userSectorLinks.get(1).getUserId(), is(userId));
    }

    @Test
    public void createUserSectorLinkModelsNotAllSectorsFoundTest() {
        var userId = new Random().nextInt();

        var sector1 = createSectorModel();
        var sector2 = createSectorModel();
        var sectors = List.of(sector1);

        var sectorId1 = sector1.getId();
        var sectorId2 = sector2.getId();
        var sectorIds = Set.of(sectorId1, sectorId2);

        assertThrows(BadRequestException.class, () -> coreUserSectorServiceTest.createUserSectorLinkModels(sectorIds, userId, sectors));
    }

    private static class TestCoreUserSectorServiceTest extends CoreUserSectorService {

        public TestCoreUserSectorServiceTest(
                UserRepository userRepository,
                SectorRepository sectorRepository,
                UserSectorLinkRepository userSectorLinkRepository
        ) {
            super(userRepository, sectorRepository, userSectorLinkRepository);
        }
    }
}
