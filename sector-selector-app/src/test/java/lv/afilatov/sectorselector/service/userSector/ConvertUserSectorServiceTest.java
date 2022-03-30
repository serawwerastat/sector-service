package lv.afilatov.sectorselector.service.userSector;

import static lv.afilatov.sectorselector.test_util.userSector.TestUserSectorAssertions.fieldsAreEqual;
import static lv.afilatov.sectorselector.test_util.userSector.TestUserSectorConstructors.createUserModel;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.ws.rs.InternalServerErrorException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ConvertUserSectorServiceTest {

    private final ConvertUserSectorService convertUserSectorService = new ConvertUserSectorService();

    @Test
    public void convertUserModelToUserSectorResponseTest() {
        var user = createUserModel();
        var response = convertUserSectorService.convertUserModelToUserSectorResponse(user);
        fieldsAreEqual(user, response);
    }

    @Test
    public void convertUserModelToUserSectorResponseNullUserTest() {
        assertThrows(InternalServerErrorException.class, () -> convertUserSectorService.convertUserModelToUserSectorResponse(null));
    }
}
