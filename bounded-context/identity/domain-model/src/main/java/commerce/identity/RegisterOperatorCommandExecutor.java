package commerce.identity;

import commerce.InvariantViolationException;
import commerce.command.AggregateCommand;
import commerce.identity.command.RegisterOperator;

public class RegisterOperatorCommandExecutor {

    private final OperatorRepository repository;

    public RegisterOperatorCommandExecutor(OperatorRepository repository) {
        this.repository = repository;
    }

    public void execute(AggregateCommand<RegisterOperator> command) {
        if (command.payload().username() == null ||
            command.payload().passwordHash() == null) {
            throw new InvariantViolationException();
        }

        var operator = new Operator(
            command.aggregateId(),
            command.payload().username(),
            command.payload().passwordHash());

        repository.create(operator);
    }
}
