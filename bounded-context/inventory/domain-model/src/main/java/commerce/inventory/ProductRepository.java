package commerce.inventory;

import java.util.UUID;
import java.util.function.Consumer;

public interface ProductRepository {

    void create(Product product);

    void update(
        UUID id,
        Consumer<Product> updater,
        Consumer<UUID> notFoundHandler);
}
