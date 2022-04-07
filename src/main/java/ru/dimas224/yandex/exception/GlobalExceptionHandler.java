package ru.dimas224.yandex.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.dimas224.yandex.util.GlobalError;

@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleMethodArgumentsValidationExceptions(
          MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
            .getAllErrors()
            .forEach(
                    (error) -> {
                      String fieldName = ((FieldError) error).getField();
                      String errorMessage = error.getDefaultMessage();
                      errors.put("error", fieldName + ": " + errorMessage);
                    });
    return errors;
  }

  @ExceptionHandler(GlobalException.class)
  public ResponseEntity<GlobalError> handleGlobalException(GlobalException exception) {
    return handleException(exception, exception.getHttpStatus());
  }

  private ResponseEntity<GlobalError> handleException(Exception exception, HttpStatus errorStatus) {
    Throwable cause = exception.getCause();
    if (cause == null) {
      return ResponseEntity.status(errorStatus)
              .contentType(MediaType.APPLICATION_JSON)
              .body(new GlobalError(exception.getMessage()));
    } else {
      return ResponseEntity.status(errorStatus)
              .contentType(MediaType.APPLICATION_JSON)
              .body(new GlobalError(exception.getMessage() + cause.getMessage()));
    }
  }
}
