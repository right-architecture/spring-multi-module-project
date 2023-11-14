package commerce.identity.jpa;

import commerce.identity.commandmodel.User;
import commerce.identity.commandmodel.UserRepository;

public class UserRepositoryAdapter implements UserRepository {

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
}
