package like.redis.command;

import like.redis.command.impl.*;
import like.redis.command.impl.string.Get;
import like.redis.command.impl.string.Set;

import java.util.function.Supplier;

/**
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2022/1/3 10:55
 */
public enum CommandType {

    // common
    ping(Ping::new), client(Client::new), config(Config::new), info(Info::new), scan(Scan::new),
    // command(CommandImpl::new)

    // string
    set(Set::new), get(Get::new);

    private final Supplier<Command> supplier;

    CommandType(Supplier<Command> supplier) {
        this.supplier = supplier;
    }

    public Supplier<Command> getSupplier() {
        return supplier;
    }
}