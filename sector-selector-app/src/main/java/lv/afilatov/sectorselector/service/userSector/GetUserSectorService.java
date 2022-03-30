package lv.afilatov.sectorselector.service.userSector;

import static java.util.stream.Collectors.toSet;

import java.util.List;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lv.afilatov.sectorselector.domain.model.UserModel;
import lv.afilatov.sectorselector.domain.model.UserSectorLinkModel;
import lv.afilatov.sectorselector.repository.UserRepository;
import lv.afilatov.sectorselector.repository.UserSectorLinkRepository;

@Component
public class GetUserSectorService extends CoreUserSectorService {

    @Autowired
    public GetUserSectorService(
            UserRepository userRepository,
            UserSectorLinkRepository userSectorLinkRepository
    ) {
        super(userRepository, userSectorLinkRepository);
    }

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    public UserModel getUserModelBy(int id) {
        var userModel = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found. User id: " + id));
        var userSectorIds = userSectorLinkRepository.findByUserId(id)
                .stream()
                .map(UserSectorLinkModel::getSectorId)
                .collect(toSet());

        if (userSectorIds.isEmpty()) {
            throw new InternalServerErrorException("No sectors linked to user. Should have at least one");
        }

        return userModel.addSectorIds(userSectorIds);
    }
}
