package lv.afilatov.sectorselector.IT;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;

import lv.afilatov.sectorselector.domain.response.SectorResponse;
import lv.afilatov.sectorselector.repository.SectorRepository;

public class SectorControllerIT extends RestServiceTest {

    @Inject
    private SectorRepository sectorRepository;

    @Test
    public void getAllSectorsTest() throws Exception {
        var result = mockMvc.perform(getAllSectors())
                .andExpect(status().isOk())
                .andReturn();
        var response = result.getResponse().getContentAsString();
        var sectorResponses = objectMapper.readValue(response, new TypeReference<List<SectorResponse>>() {
        });
        var allSectors = sectorRepository.findAll();
        assertThat(sectorResponses.size(), is(allSectors.size()));

    }
}
