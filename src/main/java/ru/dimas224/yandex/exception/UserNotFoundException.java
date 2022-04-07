package ru.dimas224.yandex.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends GlobalException {
  public UserNotFoundException(String message) {
    super(HttpStatus.BAD_REQUEST, message);
  }
}
