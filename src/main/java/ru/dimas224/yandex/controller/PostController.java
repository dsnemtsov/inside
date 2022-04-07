package ru.dimas224.yandex.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.dimas224.yandex.model.InputMessage;
import ru.dimas224.yandex.service.PostService;

@RestController
@AllArgsConstructor
public class PostController {

  private PostService postService;

  @PostMapping("/posts")
  public ResponseEntity<Object> addMessage(@RequestBody InputMessage inputMessage) {
    if (inputMessage.getMessage().equals("history 10")) {
      return ResponseEntity.ok(postService.getMessages(inputMessage));
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(postService.addMessage(inputMessage));
  }
}
