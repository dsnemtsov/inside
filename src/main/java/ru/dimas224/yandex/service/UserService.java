package ru.dimas224.yandex.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dimas224.yandex.entity.UserEntity;
import ru.dimas224.yandex.exception.InvalidCredentialsException;
import ru.dimas224.yandex.exception.UserAlreadyExistsException;
import ru.dimas224.yandex.repository.UserRepo;
import ru.dimas224.yandex.security.JwtTokenProvider;

@Service
@AllArgsConstructor
public class UserService {
  private final UserRepo userRepo;
  private final JwtTokenProvider jwtTokenProvider;

  public UserEntity register(UserEntity user) {
    if (userRepo.findByName(user.getName()).isPresent()) {
      throw new UserAlreadyExistsException("User with this login already exists!");
    }
    return userRepo.save(user);
  }

  public String login(UserEntity user) {
    if (userRepo.findByNameAndPassword(user.getName(), user.getPassword()).isEmpty()) {
      throw new InvalidCredentialsException("Wrong password");
    }

    return jwtTokenProvider.createToken(user.getName());
  }
}
