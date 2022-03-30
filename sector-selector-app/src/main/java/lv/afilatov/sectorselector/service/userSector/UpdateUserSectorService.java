package lv.afilatov.sectorselector.service.userSector;

import javax.ws.rs.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lv.afilatov.sectorselector.domain.model.UserModel;
import lv.afilatov.sectorselector.domain.request.UserSectorRequest;
import lv.afilatov.sectorselector.repository.SectorRepository;
import lv.afilatov.sectorselector.repository.UserRepository;
import lv.afilatov.sectorselector.repository.UserSectorLinkRepository;

@Component
public class UpdateUserSectorService extends CoreUserSectorService {

    @Autowired
    public UpdateUserSectorService(
            UserRepository userRepository,
            SectorRepository sectorRepository,
            UserSectorLinkRepository userSectorLinkRepository) {
        super(userRepository, sectorRepository, userSectorLinkRepository);
    }

    public UserModel updateUserSector(UserSectorRequest request, int id) {
        var userModel = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found. User id: " + id));

        var sectorModels = sectorRepository.findByIdIn(request.getSectors());

        validateFoundSectors(request, sectorModels);

        userModel.setName(request.getName());
        var userSectorLinks = createUserSectorLinkModels(request.getSectors(), id, sectorModels);

        userRepository.save(userModel);
        userSectorLinkRepository.deleteByUserId(id);
        userSectorLinkRepository.saveAll(userSectorLinks);

        return userModel.addSectorIds(request.getSectors());
    }
}
