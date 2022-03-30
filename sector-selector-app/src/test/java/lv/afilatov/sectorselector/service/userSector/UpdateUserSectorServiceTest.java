package lv.afilatov.sectorselector.service.userSector;

import static java.util.Collections.emptyList;
import static lv.afilatov.sectorselector.test_util.sector.TestSectorConstructors.createSectorModel;
import static lv.afilatov.sectorselector.test_util.userSector.TestUserSectorConstructors.creatUserSectorRequest;
import static lv.afilatov.sectorselector.test_util.userSector.TestUserSectorConstructors.createUserModel;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import lv.afilatov.sectorselector.repository.SectorRepository;
import lv.afilatov.sectorselector.repository.UserRepository;
import lv.afilatov.sectorselector.repository.UserSectorLinkRepository;

@ExtendWith(MockitoExtension.class)
public class UpdateUserSectorServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final SectorRepository sectorRepository = mock(SectorRepository.class);
    private final UserSectorLinkRepository userSectorLinkRepository = mock(UserSectorLinkRepository.class);

    private final UpdateUserSectorService updateUserSectorService =
            new UpdateUserSectorService(
                    userRepository,
                    sectorRepository,
                    userSectorLinkRepository
            );

    @Test
    public void updateUserSectorTest() {
        var user = createUserModel();
        var userId = user.getId();
        var newSector = createSectorModel();
        var newSectorId = newSector.getId();
        var userSectorRequest = creatUserSectorRequest(Set.of(newSectorId));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(sectorRepository.findByIdIn(userSectorRequest.getSectors())).thenReturn(List.of(newSector));
        var updatedUser = updateUserSectorService.updateUserSector(userSectorRequest, userId);
        assertThat(updatedUser.getName(), is(userSectorRequest.getName()));
        assertThat(updatedUser.getSectorIds().contains(newSector.getId()), is(true));

        verify(userRepository).save(any());
        verify(userSectorLinkRepository).deleteByUserId(userId);
        verify(userSectorLinkRepository).saveAll(any());
    }

    @Test
    public void updateUserSectorUserNotFoundTest() {
        var user = createUserModel();
        var userId = user.getId();
        var userSectorRequest = creatUserSectorRequest(user);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> updateUserSectorService.updateUserSector(userSectorRequest, userId));

        verify(userRepository, times(0)).save(any());
        verifyNoInteractions(userSectorLinkRepository);
        verifyNoInteractions(userSectorLinkRepository);
    }

    @Test
    public void updateUserSectorOneSectorNotExistTest() {
        var user = createUserModel();
        var userId = user.getId();
        var newSector = createSectorModel();
        var newSectorId = newSector.getId();
        var userSectorRequest = creatUserSectorRequest(Set.of(newSectorId));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(sectorRepository.findByIdIn(userSectorRequest.getSectors())).thenReturn(emptyList());
        assertThrows(BadRequestException.class, () -> updateUserSectorService.updateUserSector(userSectorRequest, userId));

        verify(userRepository, times(0)).save(any());
        verifyNoInteractions(userSectorLinkRepository);
        verifyNoInteractions(userSectorLinkRepository);
    }
}
