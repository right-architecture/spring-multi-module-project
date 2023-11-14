package commerce.operation.api.controller;

import commerce.identity.OperatorRepository;
import commerce.identity.RegisterOperatorCommandExecutor;
import commerce.identity.command.RegisterOperator;
import commerce.operation.api.command.RegisterNewOperator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/operators")
public class OperatorsController {

    private final OperatorRepository repository;

    public OperatorsController(OperatorRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterNewOperator request) {
        var executor = new RegisterOperatorCommandExecutor(repository);
        String passwordHash = request.password();
        UUID operatorId = UUID.randomUUID();
        var command = new RegisterOperator(request.username(), passwordHash);
        executor.execute(operatorId, command);
    }
}
