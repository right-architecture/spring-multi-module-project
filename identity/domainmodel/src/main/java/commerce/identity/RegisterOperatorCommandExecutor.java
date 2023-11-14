package commerce.identity;

import commerce.InvariantViolationException;
import commerce.identity.command.RegisterOperator;

import java.util.UUID;

public class RegisterOperatorCommandExecutor {

    private final OperatorRepository repository;

    public RegisterOperatorCommandExecutor(OperatorRepository repository) {
        this.repository = repository;
    }

    public void execute(UUID id, RegisterOperator command) {
        if (command.username() == null || command.passwordHash() == null) {
            throw new InvariantViolationException();
        }

        var operator = new Operator(
            id,
            command.username(),
            command.passwordHash());

        repository.create(operator);
    }
}
