package ru.ildar.georgii.rest;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.ildar.georgii.dto.ApplicationRequestDTO;
import ru.ildar.georgii.dto.ApplicationResponseDTO;
import ru.ildar.georgii.entity.ApplicationStatus;
import ru.ildar.georgii.service.ApplicationService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(Controller.class)
class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ApplicationService applicationService;

    @Test
    void getAllApplications() throws Exception {

        ApplicationResponseDTO first = new ApplicationResponseDTO();
        first.setId(1L);
        first.setTitle("Laptop");
        first.setDescription("A powerful gaming laptop");
        first.setStatus(ApplicationStatus.CREATED);

        ApplicationResponseDTO second = new ApplicationResponseDTO();
        second.setId(2L);
        second.setTitle("Phone");
        second.setStatus(ApplicationStatus.ASSIGNED);

        Mockito.when(applicationService.findAll())
                .thenReturn(List.of(first, second));

        mockMvc.perform(get("/api/applications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Laptop"))
                .andExpect(jsonPath("$[0].status").value("CREATED"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("Phone"));

    }

    @Test
    void getApplicationById() throws Exception {

        ApplicationResponseDTO mockResponse = new ApplicationResponseDTO();
        mockResponse.setId(1L);
        mockResponse.setTitle("Laptop");
        mockResponse.setDescription("A powerful gaming laptop");
        mockResponse.setStatus(ApplicationStatus.CREATED);

        Mockito.when(applicationService.findById(anyLong()))
                .thenReturn(Optional.of(mockResponse));

        mockMvc.perform(get("/api/applications/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Laptop"))
                .andExpect(jsonPath("$.description").value("A powerful gaming laptop"))
                .andExpect(jsonPath("$.status").value("CREATED"));

    }

    @Test
    void createApplication() throws Exception {

        ApplicationResponseDTO saved = new ApplicationResponseDTO();
        saved.setId(1L);
        saved.setTitle("Laptop");
        saved.setDescription("A powerful gaming laptop");
        saved.setStatus(ApplicationStatus.CREATED);

        Mockito.when(applicationService.save(any(ApplicationRequestDTO.class)))  // любой request
                .thenReturn(saved);

        String requestBody = """
                {
                  "title": "Laptop",
                  "description": "A powerful gaming laptop",
                  "status": "CREATED"
                }
                """;

        mockMvc.perform(post("/api/applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Laptop"))
                .andExpect(jsonPath("$.status").value("CREATED"));

    }

    @Test
    void updateApplication() throws Exception {

        ApplicationResponseDTO updated = new ApplicationResponseDTO();
        updated.setId(1L);
        updated.setTitle("Laptop UPDATED");
        updated.setDescription("changed description");
        updated.setStatus(ApplicationStatus.BEGAN);

        Mockito.when(applicationService.update(anyLong(), any(ApplicationRequestDTO.class)))
                .thenReturn(Optional.of(updated));

        String requestBody = """
                {
                  "title": "Laptop UPDATED",
                  "description": "changed description",
                  "status": "BEGAN"
                }
                """;

        mockMvc.perform(put("/api/applications/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Laptop UPDATED"))
                .andExpect(jsonPath("$.status").value("BEGAN"));

    }

    @Test
    void deleteApplication() throws Exception {

        Mockito.when(applicationService.findById(1L))
                .thenReturn(Optional.of(new ApplicationResponseDTO()));

        mockMvc.perform(delete("/api/applications/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(applicationService).delete(1L);  
    }

}
