package lv.afilatov.sectorselector.test_util.userSector;

import static java.util.Collections.emptySet;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.util.Random;
import java.util.Set;

import lv.afilatov.sectorselector.domain.model.UserModel;
import lv.afilatov.sectorselector.domain.model.UserSectorLinkModel;
import lv.afilatov.sectorselector.domain.request.UserSectorRequest;
import lv.afilatov.sectorselector.domain.response.UserSectorResponse;

public class TestUserSectorConstructors {

    public static UserModel createUserModel() {
        return createUserModel(emptySet());
    }

    public static UserModel createUserModel(Set<Integer> sectorIds) {
        return new UserModel()
                .setId(new Random().nextInt())
                .setName("user-name-" + randomAlphabetic(4))
                .addSectorIds(sectorIds)
                .setAgreementAccepted(true);
    }

    public static UserSectorLinkModel createUserSectorLinkModel(int userId) {
        return new UserSectorLinkModel()
                .setId(new Random().nextInt())
                .setUserId(userId)
                .setSectorId(new Random().nextInt());
    }

    public static UserSectorRequest creatUserSectorRequest(UserModel userModel) {
        return new UserSectorRequest()
                .setName(userModel.getName())
                .setSectors(userModel.getSectorIds())
                .setAgreementAccepted(true);
    }

    public static UserSectorRequest creatUserSectorRequest(Set<Integer> sectorIds) {
        return new UserSectorRequest()
                .setName("user-name-" + randomAlphabetic(4))
                .setSectors(sectorIds)
                .setAgreementAccepted(true);
    }

    public static UserSectorResponse createUserSectorResponse(UserModel userModel) {
        return new UserSectorResponse()
                .setUserId(userModel.getId())
                .setName(userModel.getName())
                .setAgreementAccepted(userModel.isAgreementAccepted())
                .setSectors(emptySet());
    }
}
