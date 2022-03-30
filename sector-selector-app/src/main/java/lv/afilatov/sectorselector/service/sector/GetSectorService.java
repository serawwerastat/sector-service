package lv.afilatov.sectorselector.service.sector;

import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ws.rs.InternalServerErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lv.afilatov.sectorselector.domain.model.SectorLinkModel;
import lv.afilatov.sectorselector.domain.model.SectorModel;
import lv.afilatov.sectorselector.repository.SectorLinkRepository;
import lv.afilatov.sectorselector.repository.SectorRepository;

@Component
public class GetSectorService {

    private final SectorLinkRepository sectorLinkRepository;
    private final SectorRepository sectorRepository;

    @Autowired
    public GetSectorService(
            SectorLinkRepository sectorLinkRepository,
            SectorRepository sectorRepository
    ) {
        this.sectorLinkRepository = sectorLinkRepository;
        this.sectorRepository = sectorRepository;
    }

    public List<SectorModel> getAllSectorModels() {
        var sectors = sectorRepository.findAll();
        var sectorLinks = sectorLinkRepository.findAll();

        var sectorsMappedById = sectors.stream()
                .collect(toMap(SectorModel::getId, Function.identity()));
        var sectorsLinksMappedByChildId = sectorLinks.stream()
                .collect(toMap(SectorLinkModel::getChildId, Function.identity()));

        validateSectorLinks(sectorLinks, sectorsMappedById);

        return sectors.stream()
                .map(sector -> isParentless(sector, sectorsLinksMappedByChildId) ? sector
                        : addChildToParent(sector, sectorsMappedById, sectorsLinksMappedByChildId))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private void validateSectorLinks(List<SectorLinkModel> sectorLinks, Map<Integer, SectorModel> sectorsMappedById) {
        sectorLinks.forEach(sectorLink -> {
            var childSector = sectorsMappedById.get(sectorLink.getChildId());
            var parentSector = sectorsMappedById.get(sectorLink.getParentId());

            if (childSector == null || parentSector == null) {
                var errorMsg = String.format("Could not link child and parent sector. "
                                + "ChildId: %d. ParentId: %s. Found child: %s. Found parent: %s",
                        sectorLink.getChildId(), sectorLink.getParentId(), childSector, parentSector);
                throw new InternalServerErrorException(errorMsg);
            }
        });
    }

    private boolean isParentless(SectorModel sector, Map<Integer, SectorLinkModel> sectorsLinksMappedByChildId) {
        return sectorsLinksMappedByChildId.get(sector.getId()) == null;
    }

    private SectorModel addChildToParent(
            SectorModel sector,
            Map<Integer, SectorModel> sectorsMappedById,
            Map<Integer, SectorLinkModel> sectorsLinksMappedByChildId
    ) {
        var childId = sector.getId();
        var parentId = sectorsLinksMappedByChildId.get(childId).getParentId();
        var parentSector = sectorsMappedById.get(parentId);
        parentSector.addChildSector(sector);
        return null;
    }
}
