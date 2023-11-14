package commerce.identity;

import jakarta.persistence.Column;
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

    @Column(length = 1000)
    private String passwordHash;
}
