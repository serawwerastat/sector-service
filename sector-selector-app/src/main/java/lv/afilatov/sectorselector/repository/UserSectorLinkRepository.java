package lv.afilatov.sectorselector.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import lv.afilatov.sectorselector.domain.model.UserSectorLinkModel;

public interface UserSectorLinkRepository extends CrudRepository<UserSectorLinkModel, Integer> {

    List<UserSectorLinkModel> findAll();

    List<UserSectorLinkModel> findByUserId(int userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_to_sector WHERE user_id = ?1", nativeQuery = true)
    void deleteByUserId(int userId);
}
