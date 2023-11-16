package commerce.command;

import java.util.UUID;

public record AggregateCommand<T>(UUID aggregateId, T payload) {
}
