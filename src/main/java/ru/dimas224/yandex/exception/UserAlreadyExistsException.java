package ru.dimas224.yandex.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends GlobalException {
  public UserAlreadyExistsException(String message) {
    super(HttpStatus.BAD_REQUEST, message);
  }
}
