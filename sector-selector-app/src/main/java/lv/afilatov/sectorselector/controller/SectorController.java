package lv.afilatov.sectorselector.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lv.afilatov.sectorselector.domain.response.SectorResponse;
import lv.afilatov.sectorselector.service.sector.SectorService;

@CrossOrigin
@RestController
@RequestMapping(path = "api/sector", produces = MediaType.APPLICATION_JSON_VALUE)
public class SectorController {

    @Inject
    private SectorService sectorService;

    @GetMapping
    public List<SectorResponse> getAllSectors() {
        return sectorService.getAllSectors();
    }
}
