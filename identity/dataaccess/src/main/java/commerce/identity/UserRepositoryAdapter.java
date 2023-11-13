package commerce.identity;

import commerce.identity.commandmodel.User;
import commerce.identity.commandmodel.UserRepository;
import commerce.identity.querymodel.UserReader;
import commerce.identity.view.UserView;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public class UserRepositoryAdapter implements UserRepository, UserReader {

    private final UserJpaRepository repository;

    public UserRepositoryAdapter(UserJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setEmail(user.getEmail());
        entity.setPasswordHash(user.getPasswordHash());
        repository.save(entity);
    }

    @Override
    public Optional<UserView> findById(UUID id) {
        return repository.findById(id).map(UserEntity::toView);
    }

    @Override
    public Optional<UserView> findByCredentials(
        String email,
        Function<String, Boolean> passwordVerifier
    ) {
        return repository
            .findByEmail(email)
            .filter(user -> passwordVerifier.apply(user.getPasswordHash()))
            .map(UserEntity::toView);
    }
}
