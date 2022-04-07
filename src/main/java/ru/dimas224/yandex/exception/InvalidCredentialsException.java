package ru.dimas224.yandex.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends GlobalException {
  public InvalidCredentialsException(String message) {
    super(HttpStatus.BAD_REQUEST, message);
  }
}
