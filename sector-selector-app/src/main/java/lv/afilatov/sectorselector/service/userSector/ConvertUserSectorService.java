package lv.afilatov.sectorselector.service.userSector;

import static java.util.stream.Collectors.toSet;

import javax.ws.rs.InternalServerErrorException;

import org.springframework.stereotype.Component;

import lv.afilatov.sectorselector.domain.model.UserModel;
import lv.afilatov.sectorselector.domain.response.UserSectorResponse;

@Component
public class ConvertUserSectorService {

    UserSectorResponse convertUserModelToUserSectorResponse(UserModel userModel) {
        if (userModel == null) {
            throw new InternalServerErrorException("Null user received");
        }

        var sectorStringIds = userModel.getSectorIds()
                .stream()
                .map(String::valueOf)
                .collect(toSet());
        return new UserSectorResponse()
                .setUserId(userModel.getId())
                .setName(userModel.getName())
                .setSectors(sectorStringIds)
                .setAgreementAccepted(userModel.isAgreementAccepted());
    }
}
