package commerce.identity.commandmodel;

import commerce.identity.command.CreateUser;

import java.util.UUID;

public class CreateUserCommandExecutor {

    private final UserRepository repository;

    public CreateUserCommandExecutor(UserRepository repository) {
        this.repository = repository;
    }

    public void execute(UUID id, CreateUser command) {
        User user = new User(id, command.email(), command.passwordHash());
        repository.create(user);
    }
}
