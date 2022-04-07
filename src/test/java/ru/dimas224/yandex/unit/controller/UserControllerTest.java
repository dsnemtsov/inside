package ru.dimas224.yandex.unit.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.dimas224.yandex.controller.UserController;
import ru.dimas224.yandex.entity.UserEntity;
import ru.dimas224.yandex.service.UserService;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserControllerTest {
  private final UserService userService = mock(UserService.class);
  private final UserController userController = new UserController(userService);

  UserEntity user;

  @BeforeEach
  void setUp() {
    user = new UserEntity();
  }

  @Test
  void shouldRegister() {
    ResponseEntity response = userController.register(user);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    verify(userService).register(user);
  }

  @Test
  void shouldLogin() {
    ResponseEntity response = userController.login(user);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(userService).login(user);
  }
}
