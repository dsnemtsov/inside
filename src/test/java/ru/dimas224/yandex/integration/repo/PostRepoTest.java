package ru.dimas224.yandex.integration.repo;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.dimas224.yandex.entity.PostEntity;
import ru.dimas224.yandex.entity.UserEntity;
import ru.dimas224.yandex.integration.IntegrationTestBase;
import ru.dimas224.yandex.repository.PostRepo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PostRepoTest extends IntegrationTestBase {
  private static final Long USER_ID = 1L;
  private static final String NAME = "user";
  private static final String PASSWORD = "password";
  private static final String MESSAGE = "newMessage";

  @Autowired
  private PostRepo postRepo;

  @Test
  void shouldFindTop10ByUserIdOrderByIdDesc() {
    List<PostEntity> result = postRepo.findTop10ByUserIdOrderByIdDesc(USER_ID);

    assertThat(result, hasSize(10));
    result.forEach(post -> {
      assertEquals(USER_ID, post.getUser().getId());
    });
  }

  @Test
  void shouldSavePost() {
    UserEntity user = UserEntity.builder()
            .name(NAME)
            .password(PASSWORD)
            .build();

    PostEntity post = PostEntity.builder()
            .message(MESSAGE)
            .user(user)
            .build();
    postRepo.save(post);

    assertNotNull(post.getId());
  }
}
