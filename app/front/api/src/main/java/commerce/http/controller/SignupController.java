package commerce.http.controller;

import commerce.http.command.Signup;
import commerce.identity.CreateUserCommandExecutor;
import commerce.identity.UserRepository;
import commerce.identity.command.CreateUser;
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
    public void signup(@RequestBody Signup request) {
        UUID userId = UUID.randomUUID();
        String passwordHash = request.password() == null
            ? null
            : passwordEncoder.encode(request.password());
        var command = new CreateUser(request.email(), passwordHash);
        new CreateUserCommandExecutor(repository).execute(userId, command);
    }
}
