package io.hhplus.concert.domain.respository;

import io.hhplus.concert.domain.entity.User;

public interface UserRepository {
    User findById(Long userId);
}
