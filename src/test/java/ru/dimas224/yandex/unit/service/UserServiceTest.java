package ru.dimas224.yandex.unit.service;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.dimas224.yandex.entity.UserEntity;
import ru.dimas224.yandex.exception.InvalidCredentialsException;
import ru.dimas224.yandex.exception.UserAlreadyExistsException;
import ru.dimas224.yandex.repository.UserRepo;
import ru.dimas224.yandex.security.JwtTokenProvider;
import ru.dimas224.yandex.service.UserService;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {
  private final UserRepo userRepo = mock(UserRepo.class);
  private final JwtTokenProvider jwtTokenProvider = mock(JwtTokenProvider.class);
  private final UserService userService = new UserService(userRepo, jwtTokenProvider);
  private UserEntity user;

  private final static String NAME = "user";
  private final static String PASSWORD = "password";

  @BeforeEach
  void setUp() {
    user = UserEntity.builder()
            .name(NAME)
            .password(PASSWORD)
            .build();
  }

  @Test
  void shouldRegister() {
    when(userRepo.findByName(any())).thenReturn(Optional.empty());

    userService.register(user);

    verify(userRepo).save(user);
  }

  @Test
  void shouldNotRegister() {
    when(userRepo.findByName(any())).thenReturn(Optional.of(new UserEntity()));

    assertThatExceptionOfType(UserAlreadyExistsException.class)
            .isThrownBy(() -> userService.register(user))
            .withMessage("User with this login already exists!");
  }

  @Test
  void shouldLogin() {
    when(userRepo.findByNameAndPassword(NAME,PASSWORD)).thenReturn(Optional.of(user));

    userService.login(user);

    verify(userRepo).findByNameAndPassword(user.getName(), user.getPassword());
    verify(jwtTokenProvider).createToken(user.getName());
  }

  @Test
  void shouldNotLogin() {
    when(userRepo.findByNameAndPassword(NAME, PASSWORD)).thenReturn(Optional.empty());

    assertThatExceptionOfType(InvalidCredentialsException.class)
            .isThrownBy(() -> userService.login(user))
            .withMessage("Wrong password");
  }
}