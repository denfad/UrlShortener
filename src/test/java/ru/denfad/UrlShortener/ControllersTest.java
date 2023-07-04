package ru.denfad.UrlShortener;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.denfad.UrlShortener.controllers.RedirectController;
import ru.denfad.UrlShortener.dto.GenerateDTO;
import ru.denfad.UrlShortener.service.UrlService;

@WebMvcTest
public class ControllersTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${url.shortener.host}")
    private String host;

    private static ObjectMapper mapper = new ObjectMapper();


    @Test
    public void testGenerateValidUrl() throws Exception {
        GenerateDTO url = new GenerateDTO("https://www.youtube.com/");
        String json = mapper.writeValueAsString(url);
        mockMvc.perform(post("/generate").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.url", Matchers.startsWith(host)));

        mockMvc.perform(post("/generate").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.url", Matchers.startsWith(host)));
    }

    @Test
    public void testGenerateInvalidUrl() throws Exception {
        GenerateDTO url = new GenerateDTO("h/wwwyoutubecom/");
        String json = mapper.writeValueAsString(url);
        mockMvc.perform(post("/generate").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    public void testRedirect() throws Exception {
        mockMvc.perform(get("/r/aaaaaab")).andExpect(status().is3xxRedirection());
    }

    @Test
    public void testNotFoundRedirect() throws Exception {
        mockMvc.perform(get("/r/xxxxxxxxxxxxxxxx")).andExpect(status().isNotFound());
    }

    @Test
    public void testStats() throws Exception {
        mockMvc.perform(get("/stats").param("page", "0").param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].place", Matchers.equalTo(1)))
                .andExpect(jsonPath("$[1].place", Matchers.equalTo(2)));
    }

    @Test
    public void testInvalidStats() throws Exception {
        mockMvc.perform(get("/stats").param("page", "-1").param("size", "-1"))
                .andExpect(status().isBadRequest());
    }
}
