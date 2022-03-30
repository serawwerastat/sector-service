package lv.afilatov.sectorselector.service.userSector;

import static java.util.Collections.emptySet;
import static lv.afilatov.sectorselector.test_util.userSector.TestUserSectorConstructors.creatUserSectorRequest;
import static lv.afilatov.sectorselector.test_util.userSector.TestUserSectorConstructors.createUserModel;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import java.util.Set;

import javax.ws.rs.BadRequestException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ValidateUserSectorServiceTest {

    private final ValidateUserSectorService validateUserSectorService = new ValidateUserSectorService();

    @Test
    public void validateUserSectorRequestTest() {
        var user = createUserModel(Set.of(new Random().nextInt()));
        var request = creatUserSectorRequest(user);

        validateUserSectorService.validateUserSectorRequest(request);
    }

    @Test
    public void validateUserSectorRequestNullNameTest() {
        var user = createUserModel(Set.of(new Random().nextInt()));
        var request = creatUserSectorRequest(user).setName(null);

        assertThrows(BadRequestException.class, () -> validateUserSectorService.validateUserSectorRequest(request));
    }

    @Test
    public void validateUserSectorRequestEmptySectorsTest() {
        var user = createUserModel(Set.of(new Random().nextInt()));
        var request = creatUserSectorRequest(user).setSectors(emptySet());

        assertThrows(BadRequestException.class, () -> validateUserSectorService.validateUserSectorRequest(request));
    }

    @Test
    public void validateUserSectorRequestNullSectorsTest() {
        var user = createUserModel(Set.of(new Random().nextInt()));
        var request = creatUserSectorRequest(user).setSectors(null);

        assertThrows(BadRequestException.class, () -> validateUserSectorService.validateUserSectorRequest(request));
    }

    @Test
    public void validateUserSectorRequestAgreementNotAcceptedTest() {
        var user = createUserModel(Set.of(new Random().nextInt()));
        var request = creatUserSectorRequest(user).setAgreementAccepted(false);

        assertThrows(BadRequestException.class, () -> validateUserSectorService.validateUserSectorRequest(request));
    }
}
