package ru.dimas224.yandex.integration.repo;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.dimas224.yandex.entity.UserEntity;
import ru.dimas224.yandex.integration.IntegrationTestBase;
import ru.dimas224.yandex.repository.UserRepo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserRepoTest extends IntegrationTestBase {
  private final static String NAME = "user";
  private final static String PASSWORD = "password";
  private final static String NEW_USER_NAME = "newUser";
  private final static String NEW_USER_PASSWORD = "newPassword";
  private final static String INVALID_USER = "invalidUser";
  private final static String INVALID_PASSWORD = "invalidPassword";

  @Autowired
  private UserRepo userRepo;

  @Test
  void shouldFindUserByName() {
    Optional<UserEntity> user = userRepo.findByName(NAME);

    assertTrue(user.isPresent());
    user.ifPresent(userEntity -> {
      assertEquals(NAME, userEntity.getName());
      assertEquals(PASSWORD, userEntity.getPassword());
    });
  }

  @Test
  void shouldNotFindUserByUserName() {
    Optional<UserEntity> user = userRepo.findByName(INVALID_USER);

    assertTrue(user.isEmpty());
  }

  @Test
  void shouldFindUserByNameAndPassword() {
    Optional<UserEntity> user = userRepo.findByNameAndPassword(NAME, PASSWORD);

    assertTrue(user.isPresent());
    user.ifPresent(userEntity -> {
      assertEquals(NAME, userEntity.getName());
      assertEquals(PASSWORD, userEntity.getPassword());
    });
  }

  @Test
  void shouldNotFoundUserByNameAndPassword() {
    Optional<UserEntity> user = userRepo.findByNameAndPassword(NAME, INVALID_PASSWORD);

    assertTrue(user.isEmpty());
  }

  @Test
  void shouldSaveUser() {
    UserEntity user = UserEntity.builder()
            .name(NEW_USER_NAME)
            .password(NEW_USER_PASSWORD)
            .build();
    userRepo.save(user);

    assertNotNull(user.getId());
  }
}
