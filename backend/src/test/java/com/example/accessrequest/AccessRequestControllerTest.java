package com.example.accessrequest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccessRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createsAccessRequest() throws Exception {
        mockMvc.perform(post("/api/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"account":"max_mustermann","entitlement":"auditor","justification":"Need access for audit"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.status").value("created"))
                .andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    void rejectsBlankAccount() throws Exception {
        mockMvc.perform(post("/api/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"account\":\"  \",\"entitlement\":\"auditor\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.account").exists());
    }

    @Test
    void justificationIsOptional() throws Exception {
        mockMvc.perform(post("/api/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"account\":\"jane\",\"entitlement\":\"developer\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void listsAccessRequests() throws Exception {
        mockMvc.perform(post("/api/request")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"account\":\"list_user\",\"entitlement\":\"developer\"}"));

        mockMvc.perform(get("/api/requests"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].account").exists());
    }
}
