package lv.afilatov.sectorselector.service.userSector;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lv.afilatov.sectorselector.domain.model.UserModel;
import lv.afilatov.sectorselector.domain.request.UserSectorRequest;
import lv.afilatov.sectorselector.domain.response.UserSectorResponse;

@Component
public class UserSectorService {

    private final GetUserSectorService getUserSectorService;
    private final CreateUserSectorService createUserSectorService;
    private final UpdateUserSectorService updateUserSectorService;
    private final ConvertUserSectorService convertUserSectorService;
    private final ValidateUserSectorService validateUserSectorService;

    @Autowired
    public UserSectorService(
            GetUserSectorService getUserSectorService,
            CreateUserSectorService createUserSectorService,
            UpdateUserSectorService updateUserSectorService,
            ConvertUserSectorService convertUserSectorService,
            ValidateUserSectorService validateUserSectorService
    ) {
        this.getUserSectorService = getUserSectorService;
        this.createUserSectorService = createUserSectorService;
        this.updateUserSectorService = updateUserSectorService;
        this.convertUserSectorService = convertUserSectorService;
        this.validateUserSectorService = validateUserSectorService;
    }

    public List<UserModel> getAllUsers() {
        return getUserSectorService.getAllUsers();
    }

    public UserSectorResponse getUserModelBy(int id) {
        var userModel = getUserSectorService.getUserModelBy(id);
        return convertUserSectorService.convertUserModelToUserSectorResponse(userModel);
    }

    public UserSectorResponse createUserSector(UserSectorRequest request) {
        validateUserSectorService.validateUserSectorRequest(request);
        var userModel = createUserSectorService.createUserSector(request);
        return convertUserSectorService.convertUserModelToUserSectorResponse(userModel);
    }

    public UserSectorResponse updateUserSector(UserSectorRequest request, int id) {
        validateUserSectorService.validateUserSectorRequest(request);
        var userModel = updateUserSectorService.updateUserSector(request, id);
        return convertUserSectorService.convertUserModelToUserSectorResponse(userModel);
    }

}
