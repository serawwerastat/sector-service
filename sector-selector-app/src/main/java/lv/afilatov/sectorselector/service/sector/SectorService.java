package lv.afilatov.sectorselector.service.sector;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lv.afilatov.sectorselector.domain.response.SectorResponse;

@Component
public class SectorService {

    private final GetSectorService getSectorService;
    private final ConvertSectorService convertSectorService;

    @Autowired
    public SectorService(
            GetSectorService getSectorService,
            ConvertSectorService convertSectorService
    ) {
        this.getSectorService = getSectorService;
        this.convertSectorService = convertSectorService;
    }

    public List<SectorResponse> getAllSectors() {
        var sectors = getSectorService.getAllSectorModels();
        return convertSectorService.convertSectorModelsToSectorResponses(sectors);
    }

}
