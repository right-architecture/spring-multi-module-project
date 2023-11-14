package commerce.operation.api.controller;

import commerce.identity.OperatorRepository;
import commerce.identity.RegisterOperatorCommandExecutor;
import commerce.identity.command.RegisterOperator;
import commerce.operation.api.command.RegisterNewOperator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/operators")
public class OperatorsController {

    private final PasswordEncoder passwordEncoder;
    private final OperatorRepository repository;

    public OperatorsController(
        PasswordEncoder passwordEncoder,
        OperatorRepository repository
    ) {
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterNewOperator request) {
        var executor = new RegisterOperatorCommandExecutor(repository);
        UUID operatorId = UUID.randomUUID();
        String passwordHash = request.password() == null
            ? null
            : passwordEncoder.encode(request.password());
        var command = new RegisterOperator(request.username(), passwordHash);
        executor.execute(operatorId, command);
    }
}
