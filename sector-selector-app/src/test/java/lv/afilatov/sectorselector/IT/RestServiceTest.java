package lv.afilatov.sectorselector.IT;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lv.afilatov.sectorselector.domain.request.UserSectorRequest;

@SpringBootTest
@AutoConfigureMockMvc
public class RestServiceTest {

    @Autowired
    protected MockMvc mockMvc;

    protected ObjectMapper objectMapper = new ObjectMapper();

    protected MockHttpServletRequestBuilder getAllSectors() {
        return get("/sector", 42L)
                .contentType(MediaType.APPLICATION_JSON);
    }

    protected MockHttpServletRequestBuilder getAllUsers() {
        return get("/user", 42L)
                .contentType(MediaType.APPLICATION_JSON);
    }

    protected MockHttpServletRequestBuilder getUserSector(int id) {
        return get("/user/" + id, 42L)
                .contentType(MediaType.APPLICATION_JSON);
    }

    protected MockHttpServletRequestBuilder saveSelectedSector(UserSectorRequest request) throws JsonProcessingException {
        var json = objectMapper.writeValueAsString(request);
        return post("/user", 42L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
    }

    protected MockHttpServletRequestBuilder updateSelectedSector(int id, UserSectorRequest request) throws JsonProcessingException {
        var json = objectMapper.writeValueAsString(request);
        return post("/user/" + id, 42L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
    }
}
