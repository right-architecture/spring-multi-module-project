package commerce.identity.command;

public record CreateUser(String email, String passwordHash) {
}
