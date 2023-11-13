package commerce.http.controller;

import commerce.http.query.IssueToken;
import commerce.http.view.AccessTokenCarrier;
import commerce.identity.querymodel.UserReader;
import commerce.identity.view.UserView;
import io.jsonwebtoken.JwtBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api/issue-token")
public class IssueTokenController {

    private final UserReader reader;
    private final Supplier<JwtBuilder> jwtBuilderFactory;

    public IssueTokenController(
        UserReader userReader,
        Supplier<JwtBuilder> jwtBuilderFactory
    ) {
        reader = userReader;
        this.jwtBuilderFactory = jwtBuilderFactory;
    }

    @PostMapping
    public ResponseEntity<AccessTokenCarrier> issueToken(
        @RequestBody IssueToken query
    ) {
        Optional<UserView> queryResult = reader.findByCredentials(
            query.email(),
            passwordHash -> passwordHash.equals(query.password()));

        return queryResult
            .map(user -> {
                String token = jwtBuilderFactory
                    .get()
                    .setSubject(user.id().toString())
                    .compact();
                return ResponseEntity.ok(new AccessTokenCarrier(token));
            })
            .orElse(ResponseEntity.badRequest().build());
    }
}
