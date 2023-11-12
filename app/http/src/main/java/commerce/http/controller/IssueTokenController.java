package commerce.http.controller;

import commerce.http.query.IssueToken;
import commerce.http.view.AccessTokenCarrier;
import commerce.identity.querymodel.UserReader;
import commerce.identity.view.UserView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/issue-token")
public class IssueTokenController {

    private final UserReader reader;

    public IssueTokenController(UserReader userReader) {
        reader = userReader;
    }

    @PostMapping
    public ResponseEntity<AccessTokenCarrier> issueToken(
        @RequestBody IssueToken query
    ) {
        Optional<UserView> queryResult = reader.findByCredentials(
            query.email(),
            passwordHash -> passwordHash.equals(query.password()));

        return queryResult
            .map(user -> ResponseEntity.ok(new AccessTokenCarrier("")))
            .orElse(ResponseEntity.badRequest().build());
    }
}
