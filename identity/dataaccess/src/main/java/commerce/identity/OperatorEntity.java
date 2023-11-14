package commerce.identity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class OperatorEntity {

    @Id
    private UUID id;
    private String username;
    private String passwordHash;
}