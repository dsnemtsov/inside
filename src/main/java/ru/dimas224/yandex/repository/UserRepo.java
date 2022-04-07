package ru.dimas224.yandex.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import ru.dimas224.yandex.entity.UserEntity;

public interface UserRepo extends CrudRepository<UserEntity, Long> {
  Optional<UserEntity> findByName(String name);

  Optional<UserEntity> findByNameAndPassword(String name, String password);
}
