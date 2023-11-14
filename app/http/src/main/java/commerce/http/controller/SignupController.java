package commerce.http.controller;

import commerce.http.command.Signup;
import commerce.identity.command.CreateUser;
import commerce.identity.CreateUserCommandExecutor;
import commerce.identity.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/signup")
public class SignupController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    public SignupController(
        PasswordEncoder passwordEncoder,
        UserRepository userRepository
    ) {
        this.passwordEncoder = passwordEncoder;
        repository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Void> signup(@RequestBody Signup command) {
        if (command.email() == null || command.password() == null) {
            return ResponseEntity.badRequest().build();
        }

        var executor = new CreateUserCommandExecutor(repository);

        String passwordHash = passwordEncoder.encode(command.password());

        executor.execute(
            UUID.randomUUID(),
            new CreateUser(command.email(), passwordHash));

        return ResponseEntity.ok().build();
    }
}
