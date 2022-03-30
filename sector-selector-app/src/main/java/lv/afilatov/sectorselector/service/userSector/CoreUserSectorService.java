package lv.afilatov.sectorselector.service.userSector;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Set;

import javax.ws.rs.BadRequestException;

import lv.afilatov.sectorselector.domain.model.SectorModel;
import lv.afilatov.sectorselector.domain.model.UserSectorLinkModel;
import lv.afilatov.sectorselector.domain.request.UserSectorRequest;
import lv.afilatov.sectorselector.repository.SectorRepository;
import lv.afilatov.sectorselector.repository.UserRepository;
import lv.afilatov.sectorselector.repository.UserSectorLinkRepository;

public abstract class CoreUserSectorService {

    protected final UserRepository userRepository;
    protected final SectorRepository sectorRepository;
    protected final UserSectorLinkRepository userSectorLinkRepository;

    public CoreUserSectorService(
            UserRepository userRepository,
            UserSectorLinkRepository userSectorLinkRepository
    ) {
        this.userRepository = userRepository;
        this.sectorRepository = null;
        this.userSectorLinkRepository = userSectorLinkRepository;
    }

    public CoreUserSectorService(
            UserRepository userRepository,
            SectorRepository sectorRepository,
            UserSectorLinkRepository userSectorLinkRepository
    ) {
        this.userRepository = userRepository;
        this.sectorRepository = sectorRepository;
        this.userSectorLinkRepository = userSectorLinkRepository;
    }

    protected List<UserSectorLinkModel> createUserSectorLinkModels(Set<Integer> sectorIds, int userId, List<SectorModel> sectorModels) {

        if (sectorModels.size() != sectorIds.size()) {
            var foundSectorIds = sectorModels.stream()
                    .map(SectorModel::getId)
                    .collect(toList());
            var notFound = sectorIds.stream()
                    .filter(id -> !foundSectorIds.contains(id))
                    .collect(toList());
            throw new BadRequestException("Unknown sector id received: " + notFound.toString());
        }

        return sectorModels.stream()
                .map(sector -> new UserSectorLinkModel(userId, sector.getId()))
                .collect(toList());
    }

    protected void validateFoundSectors(UserSectorRequest request, java.util.List<SectorModel> sectorModels) {
        if (sectorModels.size() != request.getSectors().size()) {
            var foundSectorIds = sectorModels.stream()
                    .map(SectorModel::getId)
                    .collect(toList());
            var errorMsg = String.format("Not all sector ids from request exist. Request sectorIds: %s. Found sector ids: %s",
                    request.getSectors(), foundSectorIds);
            throw new BadRequestException(errorMsg);
        }
    }
}
