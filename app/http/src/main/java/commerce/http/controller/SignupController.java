package commerce.http.controller;

import commerce.http.command.Signup;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/signup")
public class SignupController {

    @PostMapping
    public ResponseEntity<Void> signup(@RequestBody Signup command) {
        if (command.email() == null || command.password() == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}
