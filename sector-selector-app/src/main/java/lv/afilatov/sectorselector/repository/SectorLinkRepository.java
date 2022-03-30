package lv.afilatov.sectorselector.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import lv.afilatov.sectorselector.domain.model.SectorLinkModel;

public interface SectorLinkRepository extends CrudRepository<SectorLinkModel, Integer> {

    List<SectorLinkModel> findAll();
}
