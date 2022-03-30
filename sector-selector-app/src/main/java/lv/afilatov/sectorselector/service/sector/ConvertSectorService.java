package lv.afilatov.sectorselector.service.sector;

import static lv.afilatov.sectorselector.domain.response.SectorResponse.fromSectorModel;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.InternalServerErrorException;

import org.springframework.stereotype.Component;

import lv.afilatov.sectorselector.domain.model.SectorModel;
import lv.afilatov.sectorselector.domain.response.SectorResponse;

@Component
public class ConvertSectorService {

    List<SectorResponse> convertSectorModelsToSectorResponses(List<SectorModel> sectors) {
        if (sectors == null) {
            throw new InternalServerErrorException("Null sectors received. Should be an ArrayList");
        }

        List<SectorResponse> responses = new ArrayList<>();

        sectors.forEach(sector -> addSectorResponse(responses, sector, 0));

        return responses;
    }

    private void addSectorResponse(List<SectorResponse> responses, SectorModel sector, int level) {
        var response = fromSectorModel(sector).setLevel(level);
        responses.add(response);
        if (!sector.getChildSectors().isEmpty()) {
            response.setParent(true);
            sector.getChildSectors().forEach(childSector -> addSectorResponse(responses, childSector, level + 1));
        }
    }
}
