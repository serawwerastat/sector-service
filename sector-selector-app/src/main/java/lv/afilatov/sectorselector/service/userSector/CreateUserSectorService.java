package lv.afilatov.sectorselector.service.userSector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lv.afilatov.sectorselector.domain.model.UserModel;
import lv.afilatov.sectorselector.domain.request.UserSectorRequest;
import lv.afilatov.sectorselector.repository.SectorRepository;
import lv.afilatov.sectorselector.repository.UserRepository;
import lv.afilatov.sectorselector.repository.UserSectorLinkRepository;

@Component
public class CreateUserSectorService extends CoreUserSectorService {

    @Autowired
    public CreateUserSectorService(
            UserRepository userRepository,
            SectorRepository sectorRepository,
            UserSectorLinkRepository userSectorLinkRepository
    ) {
        super(userRepository, sectorRepository, userSectorLinkRepository);
    }

    public UserModel createUserSector(UserSectorRequest request) {
        var sectorModels = sectorRepository.findByIdIn(request.getSectors());

        validateFoundSectors(request, sectorModels);

        var userModel = UserModel.from(request);
        var savedUserModel = userRepository.save(userModel);

        var userSectorLinks = createUserSectorLinkModels(request.getSectors(), savedUserModel.getId(), sectorModels);
        userSectorLinkRepository.saveAll(userSectorLinks);

        return savedUserModel.addSectorIds(request.getSectors());
    }

}
