package ru.dimas224.yandex.exception;

import org.springframework.http.HttpStatus;

public class JwtAuthenticationException extends GlobalException {
  public JwtAuthenticationException(String message) {
    super(HttpStatus.BAD_REQUEST, message);
  }
}
