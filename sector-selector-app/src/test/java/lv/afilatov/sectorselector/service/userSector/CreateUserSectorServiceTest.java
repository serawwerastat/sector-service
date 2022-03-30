package lv.afilatov.sectorselector.service.userSector;

import static lv.afilatov.sectorselector.test_util.sector.TestSectorConstructors.createSectorModel;
import static lv.afilatov.sectorselector.test_util.userSector.TestUserSectorConstructors.creatUserSectorRequest;
import static lv.afilatov.sectorselector.test_util.userSector.TestUserSectorConstructors.createUserModel;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;

import javax.ws.rs.BadRequestException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import lv.afilatov.sectorselector.domain.model.UserModel;
import lv.afilatov.sectorselector.repository.SectorRepository;
import lv.afilatov.sectorselector.repository.UserRepository;
import lv.afilatov.sectorselector.repository.UserSectorLinkRepository;

@ExtendWith(MockitoExtension.class)
public class CreateUserSectorServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final SectorRepository sectorRepository = mock(SectorRepository.class);
    private final UserSectorLinkRepository userSectorLinkRepository = mock(UserSectorLinkRepository.class);

    private final CreateUserSectorService createUserSectorService =
            new CreateUserSectorService(
                    userRepository,
                    sectorRepository,
                    userSectorLinkRepository
            );

    @Test
    public void createUserSectorTest() {
        var sector1 = createSectorModel();
        var sector2 = createSectorModel();
        var sectorIds = Set.of(sector1.getId(), sector2.getId());
        var user = createUserModel(sectorIds);
        var userSectorRequest = creatUserSectorRequest(user);
        when(userRepository.save(UserModel.from(userSectorRequest))).thenReturn(user);

        when(sectorRepository.findByIdIn(userSectorRequest.getSectors())).thenReturn(List.of(sector1, sector2));

        var createdUser = createUserSectorService.createUserSector(userSectorRequest);
        assertThat(createdUser, is(user));
        assertThat(createdUser.getSectorIds().size(), is(2));

        verify(userRepository).save(any());
        verify(userSectorLinkRepository).saveAll(any());
    }

    @Test
    public void createUserSectorOneSectorNotExistTest() {
        var sector1 = createSectorModel();
        var sector2 = createSectorModel();
        var sectorIds = Set.of(sector1.getId(), sector2.getId());
        var user = createUserModel(sectorIds);
        var userSectorRequest = creatUserSectorRequest(user);
        when(userRepository.save(UserModel.from(userSectorRequest))).thenReturn(user);

        when(sectorRepository.findByIdIn(userSectorRequest.getSectors())).thenReturn(List.of(sector1));

        assertThrows(BadRequestException.class, () -> createUserSectorService.createUserSector(userSectorRequest));

        verifyNoInteractions(userRepository);
        verifyNoInteractions(userSectorLinkRepository);
    }
}
