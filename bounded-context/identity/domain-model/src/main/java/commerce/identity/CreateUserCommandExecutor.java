package commerce.identity;

import commerce.InvariantViolationException;
import commerce.command.AggregateCommand;
import commerce.identity.command.CreateUser;

public class CreateUserCommandExecutor {

    private final UserRepository repository;

    public CreateUserCommandExecutor(UserRepository repository) {
        this.repository = repository;
    }

    public void execute(AggregateCommand<CreateUser> command) {
        if (command.payload().email() == null ||
            command.payload().passwordHash() == null) {
            throw new InvariantViolationException();
        }

        var user = new User(
            command.aggregateId(),
            command.payload().email(),
            command.payload().passwordHash());

        repository.create(user);
    }
}
