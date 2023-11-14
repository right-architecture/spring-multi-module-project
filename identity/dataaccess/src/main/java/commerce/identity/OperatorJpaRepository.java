package commerce.identity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OperatorJpaRepository extends
    OperatorRepository,
    JpaRepository<OperatorEntity, UUID> {

    Optional<OperatorEntity> findByUsername(String username);

    default void create(Operator operator) {
        var entity = new OperatorEntity();
        entity.setId(operator.getId());
        entity.setUsername(operator.getUsername());
        entity.setPasswordHash(operator.getPasswordHash());
        save(entity);
    }
}
