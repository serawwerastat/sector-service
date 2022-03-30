package lv.afilatov.sectorselector.service.userSector;

import static lv.afilatov.sectorselector.test_util.userSector.TestUserSectorConstructors.creatUserSectorRequest;
import static lv.afilatov.sectorselector.test_util.userSector.TestUserSectorConstructors.createUserModel;
import static lv.afilatov.sectorselector.test_util.userSector.TestUserSectorConstructors.createUserSectorResponse;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserSectorServiceTest {

    private final GetUserSectorService getUserSectorService = mock(GetUserSectorService.class);
    private final CreateUserSectorService createUserSectorService = mock(CreateUserSectorService.class);
    private final UpdateUserSectorService updateUserSectorService = mock(UpdateUserSectorService.class);
    private final ConvertUserSectorService convertUserSectorService = mock(ConvertUserSectorService.class);
    private final ValidateUserSectorService validateUserSectorService = mock(ValidateUserSectorService.class);

    private final UserSectorService userSectorService =
            new UserSectorService(
                    getUserSectorService,
                    createUserSectorService,
                    updateUserSectorService,
                    convertUserSectorService,
                    validateUserSectorService
            );

    @Test
    public void getAllUsersTest() {
        var user1 = createUserModel();
        var user2 = createUserModel();
        var users = List.of(user1, user2);
        when(getUserSectorService.getAllUsers()).thenReturn(users);

        var foundUser = userSectorService.getAllUsers();
        assertThat(foundUser, is(users));
    }

    @Test
    public void getUserModelByTest() {
        var user = createUserModel();
        var userId = user.getId();
        when(getUserSectorService.getUserModelBy(userId)).thenReturn(user);

        var userSectorResponse = createUserSectorResponse(user);
        when(convertUserSectorService.convertUserModelToUserSectorResponse(user)).thenReturn(userSectorResponse);

        var response = userSectorService.getUserModelBy(userId);
        assertThat(response, is(userSectorResponse));
    }

    @Test
    public void createUserSectorTest() {
        var user = createUserModel();
        var request = creatUserSectorRequest(user);
        when(createUserSectorService.createUserSector(request)).thenReturn(user);

        var userSectorResponse = createUserSectorResponse(user);
        when(convertUserSectorService.convertUserModelToUserSectorResponse(user)).thenReturn(userSectorResponse);

        var response = userSectorService.createUserSector(request);
        assertThat(response, is(userSectorResponse));

        verify(validateUserSectorService).validateUserSectorRequest(request);
    }

    @Test
    public void updateUserSectorTest() {
        var user = createUserModel();
        var userId = user.getId();
        var request = creatUserSectorRequest(user);
        when(updateUserSectorService.updateUserSector(request, userId)).thenReturn(user);

        var userSectorResponse = createUserSectorResponse(user);
        when(convertUserSectorService.convertUserModelToUserSectorResponse(user)).thenReturn(userSectorResponse);

        var response = userSectorService.updateUserSector(request, userId);
        assertThat(response, is(userSectorResponse));

        verify(validateUserSectorService).validateUserSectorRequest(request);
    }

}
