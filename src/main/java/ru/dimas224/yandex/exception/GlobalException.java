package ru.dimas224.yandex.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class GlobalException extends RuntimeException {
  private final HttpStatus httpStatus;

  GlobalException(HttpStatus httpStatus, String message) {
    super(message);
    this.httpStatus = httpStatus;
  }
}
