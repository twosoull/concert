package io.hhplus.concert.infrastructure.repositoryORM;

import io.hhplus.concert.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User,Long> {
    Optional<User> findById(Long id);
}
