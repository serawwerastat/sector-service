package lv.afilatov.sectorselector.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import lv.afilatov.sectorselector.domain.model.UserModel;

public interface UserRepository extends CrudRepository<UserModel, Integer> {

    List<UserModel> findAll();

    //    Optional<UserSectorModel> findById();
}
