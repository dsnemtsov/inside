package ru.dimas224.yandex.unit.controller;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.dimas224.yandex.controller.PostController;
import ru.dimas224.yandex.model.InputMessage;
import ru.dimas224.yandex.service.PostService;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PostControllerTest {
  private final PostService postService = mock(PostService.class);
  private final PostController postController = new PostController(postService);

  @Test
  void shouldGetMessages() {
    InputMessage inputMessage = new InputMessage("user", "history 10");

    ResponseEntity response = postController.addMessage(inputMessage);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(postService).getMessages(inputMessage);
  }

  @Test
  void shouldAddMessage() {
    InputMessage inputMessage = new InputMessage("user", "message");

    ResponseEntity response = postController.addMessage(inputMessage);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    verify(postService).addMessage(inputMessage);
  }
}
