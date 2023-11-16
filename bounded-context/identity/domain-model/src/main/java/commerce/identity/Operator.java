package commerce.identity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class Operator {

    private final UUID id;
    private String username;
    private String passwordHash;
}
