package lv.afilatov.sectorselector.service.userSector;

import static java.util.Collections.emptyList;
import static lv.afilatov.sectorselector.test_util.userSector.TestUserSectorConstructors.createUserModel;
import static lv.afilatov.sectorselector.test_util.userSector.TestUserSectorConstructors.createUserSectorLinkModel;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import lv.afilatov.sectorselector.repository.UserRepository;
import lv.afilatov.sectorselector.repository.UserSectorLinkRepository;

@ExtendWith(MockitoExtension.class)
public class GetUserSectorServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserSectorLinkRepository userSectorLinkRepository = mock(UserSectorLinkRepository.class);

    private final GetUserSectorService getUserSectorService = new GetUserSectorService(userRepository, userSectorLinkRepository);

    @Test
    public void getAllUsersTest() {
        var user1 = createUserModel();
        var user2 = createUserModel();

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));
        var users = getUserSectorService.getAllUsers();
        assertThat(users.size(), is(2));
        assertThat(users.get(0), is(user1));
        assertThat(users.get(1), is(user2));
    }

    @Test
    public void getUserModelByTest() {
        var user = createUserModel();
        var userId = user.getId();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        var userSectorLink1 = createUserSectorLinkModel(userId);
        var userSectorLink2 = createUserSectorLinkModel(userId);
        when(userSectorLinkRepository.findByUserId(userId)).thenReturn(List.of(userSectorLink1, userSectorLink2));
        var foundUser = getUserSectorService.getUserModelBy(userId);
        assertThat(foundUser, is(user));

        var expectedSectorIds = Set.of(userSectorLink1.getSectorId(), userSectorLink2.getSectorId());
        assertThat(foundUser.getSectorIds(), is(expectedSectorIds));
    }

    @Test
    public void getUserModelByNotFoundUserTest() {
        var user1 = createUserModel();
        var userId = user1.getId();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> getUserSectorService.getUserModelBy(userId));
        verifyNoInteractions(userSectorLinkRepository);
    }

    @Test
    public void getUserModelByNoSectorLinksTest() {
        var user1 = createUserModel();
        var userId = user1.getId();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user1));
        when(userSectorLinkRepository.findByUserId(userId)).thenReturn(emptyList());
        assertThrows(InternalServerErrorException.class, () -> getUserSectorService.getUserModelBy(userId));
    }
}
