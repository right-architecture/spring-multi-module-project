package commerce.inventory;

import commerce.command.AggregateCommand;
import commerce.inventory.command.AddProduct;

public class AddProductCommandExecutor {

    public AddProductCommandExecutor(ProductRepository repository) {
    }

    public void execute(AggregateCommand<AddProduct> command) {
    }
}
