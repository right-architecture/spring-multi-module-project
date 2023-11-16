package commerce.inventory.command;

import commerce.inventory.Price;

public record AddProduct(String name, String description, Price price) {
}
