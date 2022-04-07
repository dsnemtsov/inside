package ru.dimas224.yandex.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OutputMessage {
  private long id;
  private String name;
  private String message;
}
