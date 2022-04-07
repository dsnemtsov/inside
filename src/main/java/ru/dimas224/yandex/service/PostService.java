package ru.dimas224.yandex.service;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dimas224.yandex.entity.PostEntity;
import ru.dimas224.yandex.entity.UserEntity;
import ru.dimas224.yandex.exception.UserNotFoundException;
import ru.dimas224.yandex.model.InputMessage;
import ru.dimas224.yandex.model.OutputMessage;
import ru.dimas224.yandex.repository.PostRepo;
import ru.dimas224.yandex.repository.UserRepo;

@Service
@AllArgsConstructor
public class PostService {
  private PostRepo postRepo;
  private UserRepo userRepo;

  public OutputMessage addMessage(InputMessage message) {
    UserEntity userEntity = userRepo
            .findByName(message.getName())
            .orElseThrow(
                    () -> new UserNotFoundException("User not found"));
    PostEntity postEntity = new PostEntity();
    postEntity.setMessage(message.getMessage());
    postEntity.setUser(userEntity);

    postEntity = postRepo.save(postEntity);

    return new OutputMessage(
            postEntity.getId(),
            postEntity.getUser().getName(),
            postEntity.getMessage());
  }

  public List<OutputMessage> getMessages(InputMessage message) {
    UserEntity userEntity = userRepo
            .findByName(message.getName())
            .orElseThrow(
                    () -> new UserNotFoundException("User not found"));

    List<OutputMessage> result = new ArrayList<>();

    postRepo.findTop10ByUserIdOrderByIdDesc(userEntity.getId()).forEach(post -> {
      OutputMessage out = new OutputMessage(
              post.getId(),
              post.getUser().getName(),
              post.getMessage());
      result.add(out);
    });

    return result;
  }
}
