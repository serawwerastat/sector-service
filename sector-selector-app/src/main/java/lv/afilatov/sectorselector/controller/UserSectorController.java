package lv.afilatov.sectorselector.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lv.afilatov.sectorselector.domain.model.UserModel;
import lv.afilatov.sectorselector.domain.request.UserSectorRequest;
import lv.afilatov.sectorselector.domain.response.UserSectorResponse;
import lv.afilatov.sectorselector.service.userSector.UserSectorService;

@CrossOrigin
@RestController
@RequestMapping(path = "api/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserSectorController {

    @Inject
    private UserSectorService userSectorService;

    @GetMapping
    public List<UserModel> getAllUsers() {
        return userSectorService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserSectorResponse getUserSector(
            @PathVariable("id") int id
    ) {
        return userSectorService.getUserModelBy(id);
    }

    @PostMapping
    public UserSectorResponse saveSelectedSector(
            @RequestBody UserSectorRequest request
    ) {
        return userSectorService.createUserSector(request);
    }

    @PostMapping("/{id}")
    public UserSectorResponse updateSelectedSector(
            @PathVariable("id") int id,
            @RequestBody UserSectorRequest request
    ) {

        return userSectorService.updateUserSector(request, id);
    }

}
