package io.hhplus.concert.infrastructure.repositoryORM;

import io.hhplus.concert.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User,Long> {
}
