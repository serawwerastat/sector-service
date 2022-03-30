package lv.afilatov.sectorselector.IT;

import static java.util.stream.Collectors.toSet;
import static lv.afilatov.sectorselector.test_util.userSector.TestUserSectorAssertions.fieldsAreEqualWithoutId;
import static lv.afilatov.sectorselector.test_util.userSector.TestUserSectorConstructors.creatUserSectorRequest;
import static lv.afilatov.sectorselector.test_util.userSector.TestUserSectorConstructors.createUserModel;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;

import lv.afilatov.sectorselector.domain.model.UserModel;
import lv.afilatov.sectorselector.domain.response.SectorResponse;
import lv.afilatov.sectorselector.domain.response.UserSectorResponse;
import lv.afilatov.sectorselector.repository.SectorRepository;
import lv.afilatov.sectorselector.repository.UserRepository;

public class UserSectorControllerIT extends RestServiceTest {

    @Inject
    private UserRepository userRepository;
    @Inject
    private SectorRepository sectorRepository;

    @Test
    public void getAllUsersTest() throws Exception {
        var result = mockMvc.perform(getAllUsers())
                .andExpect(status().isOk())
                .andReturn();
        var response = result.getResponse().getContentAsString();
        var userModels = objectMapper.readValue(response, new TypeReference<List<UserModel>>() {
        });
        var allUsers = userRepository.findAll();
        assertThat(userModels.size(), is(allUsers.size()));
    }

    @Test
    public void getUserSectorTest() throws Exception {
        var getAllSectorsResult = mockMvc.perform(getAllSectors())
                .andExpect(status().isOk())
                .andReturn();
        var getAllSectorsResponse = getAllSectorsResult.getResponse().getContentAsString();
        var sectorResponses = objectMapper.readValue(getAllSectorsResponse, new TypeReference<List<SectorResponse>>() {
        });

        var sectorsIds = sectorResponses.stream()
                .limit(2)
                .map(SectorResponse::getId)
                .map(Integer::valueOf)
                .collect(toSet());

        var user = createUserModel(sectorsIds);
        var request = creatUserSectorRequest(user);
        var saveResult = mockMvc.perform(saveSelectedSector(request))
                .andExpect(status().isOk())
                .andReturn();
        var saveResponse = saveResult.getResponse().getContentAsString();
        var savedUser = objectMapper.readValue(saveResponse, new TypeReference<UserSectorResponse>() {
        });

        var getResult = mockMvc.perform(getUserSector(savedUser.getUserId()))
                .andExpect(status().isOk())
                .andReturn();
        var getResponse = getResult.getResponse().getContentAsString();
        var foundUser = objectMapper.readValue(getResponse, new TypeReference<UserSectorResponse>() {
        });
        assertThat(savedUser, is(foundUser));
    }

    @Test
    public void saveSelectedSectorTest() throws Exception {
        var getAllSectorsResult = mockMvc.perform(getAllSectors())
                .andExpect(status().isOk())
                .andReturn();
        var getAllSectorsResponse = getAllSectorsResult.getResponse().getContentAsString();
        var sectorResponses = objectMapper.readValue(getAllSectorsResponse, new TypeReference<List<SectorResponse>>() {
        });

        var sectorsIds = sectorResponses.stream()
                .limit(2)
                .map(SectorResponse::getId)
                .map(Integer::valueOf)
                .collect(toSet());

        var user = createUserModel(sectorsIds);
        var request = creatUserSectorRequest(user);
        var saveResult = mockMvc.perform(saveSelectedSector(request))
                .andExpect(status().isOk())
                .andReturn();
        var saveResponse = saveResult.getResponse().getContentAsString();
        var savedUser = objectMapper.readValue(saveResponse, new TypeReference<UserSectorResponse>() {
        });

        fieldsAreEqualWithoutId(user, savedUser);
    }

    @Test
    public void updateSelectedSectorTest() throws Exception {
        var getAllSectorsResult = mockMvc.perform(getAllSectors())
                .andExpect(status().isOk())
                .andReturn();
        var getAllSectorsResponse = getAllSectorsResult.getResponse().getContentAsString();
        var sectorResponses = objectMapper.readValue(getAllSectorsResponse, new TypeReference<List<SectorResponse>>() {
        });

        var sectorsIds = sectorResponses.stream()
                .limit(2)
                .map(SectorResponse::getId)
                .map(Integer::valueOf)
                .collect(toSet());

        var user = createUserModel(sectorsIds);
        var saveRequest = creatUserSectorRequest(user);
        var saveResult = mockMvc.perform(saveSelectedSector(saveRequest))
                .andExpect(status().isOk())
                .andReturn();
        var saveResponse = saveResult.getResponse().getContentAsString();
        var savedUser = objectMapper.readValue(saveResponse, new TypeReference<UserSectorResponse>() {
        });

        var userId = savedUser.getUserId();
        var newName = "new-name-" + RandomStringUtils.randomAlphabetic(10);
        var newSectorId = Integer.valueOf(sectorResponses.get(3).getId());
        var removedSectorId = Integer.valueOf(sectorResponses.get(0).getId());
        var updateRequest = creatUserSectorRequest(user)
                .setName(newName)
                .addSector(newSectorId)
                .removeSector(removedSectorId);

        var updateResult = mockMvc.perform(updateSelectedSector(userId, updateRequest))
                .andExpect(status().isOk())
                .andReturn();
        var updateResponse = updateResult.getResponse().getContentAsString();
        var updatedUser = objectMapper.readValue(updateResponse, new TypeReference<UserSectorResponse>() {
        });

        assertThat(updatedUser.getUserId(), is(savedUser.getUserId()));
        assertThat(updatedUser.getName(), is(not(savedUser.getName())));
        assertThat(updatedUser.getName(), is(newName));
        assertThat(updatedUser.getSectors(), is(not(savedUser.getSectors())));
        assertThat(updatedUser.getSectors().size(), is(2));
    }
}
