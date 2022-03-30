package lv.afilatov.sectorselector.test_util.userSector;

import static java.util.stream.Collectors.toSet;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import lv.afilatov.sectorselector.domain.model.UserModel;
import lv.afilatov.sectorselector.domain.response.UserSectorResponse;

public class TestUserSectorAssertions {

    public static void fieldsAreEqual(UserModel userModel, UserSectorResponse response) {
        assertThat(response.getUserId(), is(userModel.getId()));
        fieldsAreEqualWithoutId(userModel, response);
    }

    public static void fieldsAreEqualWithoutId(UserModel userModel, UserSectorResponse response) {
        assertThat(response.getName(), is(userModel.getName()));
        assertThat(response.isAgreementAccepted(), is(userModel.isAgreementAccepted()));
        var sectorStringIds = userModel.getSectorIds()
                .stream()
                .map(String::valueOf)
                .collect(toSet());
        assertThat(response.getSectors(), is(sectorStringIds));
    }
}
