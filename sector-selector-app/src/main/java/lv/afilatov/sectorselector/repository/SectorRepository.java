package lv.afilatov.sectorselector.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import lv.afilatov.sectorselector.domain.model.SectorModel;

public interface SectorRepository extends CrudRepository<SectorModel, Integer> {

    List<SectorModel> findAll();

    List<SectorModel> findByIdIn(Set<Integer> ids);
}
