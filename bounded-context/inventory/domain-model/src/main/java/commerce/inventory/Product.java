package commerce.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class Product {

    private final UUID id;
    private String name;
    private String description;
    private Price price;
}
