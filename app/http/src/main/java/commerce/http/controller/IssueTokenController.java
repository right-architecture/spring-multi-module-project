package commerce.http.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/issue-token")
public class IssueTokenController {

    @PostMapping
    public ResponseEntity<Void> issueToken() {
        return ResponseEntity.ok().build();
    }
}
