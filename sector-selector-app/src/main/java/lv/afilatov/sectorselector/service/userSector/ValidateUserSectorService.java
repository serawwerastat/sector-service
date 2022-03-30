package lv.afilatov.sectorselector.service.userSector;

import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Component;

import lv.afilatov.sectorselector.domain.request.UserSectorRequest;

@Component
public class ValidateUserSectorService {

    void validateUserSectorRequest(UserSectorRequest request) {
        if (request.getName() == null
                || request.getSectors() == null
                || request.getSectors().isEmpty()
                || !request.isAgreementAccepted()
        ) {
            throw new BadRequestException("Invalid request received. Request: " + request);
        }
    }
}
