package ru.dimas224.yandex.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.dimas224.yandex.entity.PostEntity;

public interface PostRepo extends JpaRepository<PostEntity, Long> {
  List<PostEntity> findTop10ByUserIdOrderByIdDesc(Long id);
}
