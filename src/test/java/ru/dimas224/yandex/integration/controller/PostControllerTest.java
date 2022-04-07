package ru.dimas224.yandex.integration.controller;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.dimas224.yandex.integration.IntegrationTestBase;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class PostControllerTest extends IntegrationTestBase {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void shouldAddMessage() throws Exception {
    mockMvc.perform(post("/posts")
                    .content("{\"name\":\"user\", \"message\":\"new message\"}")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", Is.is("user")))
            .andExpect(jsonPath("$.message", Is.is("new message")))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void shouldGetTenMessages() throws Exception {
    mockMvc.perform(post("/posts")
                    .content("{\"name\":\"user\", \"message\":\"history 10\"}")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()", Is.is(10)))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }
}
