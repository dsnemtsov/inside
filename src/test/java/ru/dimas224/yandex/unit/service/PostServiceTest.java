package ru.dimas224.yandex.unit.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.dimas224.yandex.entity.PostEntity;
import ru.dimas224.yandex.entity.UserEntity;
import ru.dimas224.yandex.exception.UserNotFoundException;
import ru.dimas224.yandex.model.InputMessage;
import ru.dimas224.yandex.model.OutputMessage;
import ru.dimas224.yandex.repository.PostRepo;
import ru.dimas224.yandex.repository.UserRepo;
import ru.dimas224.yandex.service.PostService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PostServiceTest {
  private final UserRepo userRepo = mock(UserRepo.class);
  private final PostRepo postRepo = mock(PostRepo.class);

  private final PostService postService = new PostService(postRepo, userRepo);

  private static final String NAME = "user";
  private static final String PASSWORD = "password";
  private static final String MESSAGE_TO_ADD = "message";
  private static final String MESSAGE_TO_GET = "history 10";
  private final static String INVALID_USER = "invalidUser";

  @Test
  void shouldAddNewMessage() {
    InputMessage inputMessage = new InputMessage(NAME, MESSAGE_TO_ADD);

    UserEntity user = UserEntity.builder()
            .name(NAME)
            .password(PASSWORD)
            .build();

    PostEntity postEntity = new PostEntity();
    postEntity.setId(1L);
    postEntity.setMessage(inputMessage.getMessage());
    postEntity.setUser(user);

    when(userRepo.findByName(inputMessage.getName())).thenReturn(Optional.of(user));
    when(postRepo.save(any())).thenReturn(postEntity);

    ArgumentCaptor<PostEntity> captor = ArgumentCaptor.forClass(PostEntity.class);
    postService.addMessage(inputMessage);

    verify(postRepo).save(captor.capture());

    assertThat(captor.getValue().getMessage()).isEqualTo(inputMessage.getMessage());
    assertThat(captor.getValue().getUser()).isEqualTo(user);
  }

  @Test
  void shouldThrowExceptionWhenUserNameInvalid_whenTryingAddNewMessage() {
    InputMessage inputMessage = new InputMessage(INVALID_USER, MESSAGE_TO_ADD);

    assertThatExceptionOfType(UserNotFoundException.class)
            .isThrownBy(() -> postService.addMessage(inputMessage))
            .withMessage("User not found");
  }

  @Test
  void shouldGetTenLastMessages() {
    InputMessage inputMessage = new InputMessage(NAME, MESSAGE_TO_GET);

    UserEntity user = UserEntity.builder()
            .id(1L)
            .name(NAME)
            .password(PASSWORD)
            .build();

    List<PostEntity> posts = Stream.of(
            PostEntity.builder()
                    .id(1L)
                    .message("message1")
                    .user(user).build(),
            PostEntity.builder()
                    .id(2L)
                    .message("message2")
                    .user(user).build(),
            PostEntity.builder()
                    .id(3L)
                    .message("message3")
                    .user(user).build(),
            PostEntity.builder()
                    .id(4L)
                    .message("message4")
                    .user(user).build(),
            PostEntity.builder()
                    .id(5L)
                    .message("message5")
                    .user(user).build(),
            PostEntity.builder()
                    .id(6L)
                    .message("message6")
                    .user(user).build(),
            PostEntity.builder()
                    .id(7L)
                    .message("message7")
                    .user(user).build(),
            PostEntity.builder()
                    .id(8L)
                    .message("message8")
                    .user(user).build(),
            PostEntity.builder()
                    .id(9L)
                    .message("message9")
                    .user(user).build(),
            PostEntity.builder()
                    .id(10L)
                    .message("message10")
                    .user(user).build()
            ).collect(Collectors.toList());

    when(userRepo.findByName(inputMessage.getName())).thenReturn(Optional.of(user));
    when(postRepo.findTop10ByUserIdOrderByIdDesc(user.getId())).thenReturn(posts);

    List<OutputMessage> result =  postService.getMessages(inputMessage);

    assertThat(result, hasSize(10));
    result.forEach(post -> {
      assertEquals(user.getName(), post.getName());
    });
  }

  @Test
  void shouldThrowExceptionWhenUserNameInvalid_whenTryingGetTenMessages() {
    InputMessage inputMessage = new InputMessage(INVALID_USER, MESSAGE_TO_ADD);

    assertThatExceptionOfType(UserNotFoundException.class)
            .isThrownBy(() -> postService.getMessages(inputMessage))
            .withMessage("User not found");
  }

}
