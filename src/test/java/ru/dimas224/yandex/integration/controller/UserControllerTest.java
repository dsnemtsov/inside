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
class UserControllerTest extends IntegrationTestBase {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void shouldRegister() throws Exception {
    mockMvc.perform(post("/users")
                    .content("{\"name\":\"newUser\", \"password\":\"newPassword\"}")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", Is.is("newUser")))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void shouldLogin() throws Exception {
    mockMvc.perform(post("/users/login")
                    .content("{\"name\":\"user\", \"password\":\"password\"}")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
  }
}
