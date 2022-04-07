package ru.dimas224.yandex.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dimas224.yandex.entity.UserEntity;
import ru.dimas224.yandex.service.UserService;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<Object> register(@RequestBody UserEntity user) {
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(user));
  }

  @PostMapping
  @RequestMapping("/login")
  public ResponseEntity<Object> login(@RequestBody UserEntity user) {
    return ResponseEntity.ok().body(userService.login(user));
  }
}
