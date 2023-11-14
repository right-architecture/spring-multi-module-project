package commerce.identity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends
    UserRepository,
    JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByEmail(String email);

    default void create(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setEmail(user.getEmail());
        entity.setPasswordHash(user.getPasswordHash());
        save(entity);
    }
}
