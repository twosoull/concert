package io.hhplus.concert.infrastructure.repositoryImpl;

import io.hhplus.concert.domain.entity.User;
import io.hhplus.concert.domain.handler.exception.RestApiException;
import io.hhplus.concert.domain.respository.UserRepository;
import io.hhplus.concert.infrastructure.repositoryORM.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static io.hhplus.concert.domain.handler.exception.errorCode.CommonErrorCode.RESOURCE_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    public void save(User user){
        userJpaRepository.save(user);
    }

    @Override
    public User findById(Long userId) {
        return userJpaRepository.findById(userId).orElseThrow(
                () -> new RestApiException(RESOURCE_NOT_FOUND)
        );
    }
}
