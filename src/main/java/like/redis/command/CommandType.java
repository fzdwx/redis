package like.redis.command;

import java.util.function.Supplier;

/**
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2022/1/3 10:55
 */
public enum CommandType {


    //
    ;

    private final Supplier<Command> supplier;

    CommandType(Supplier<Command> supplier) {
        this.supplier = supplier;
    }

    public Supplier<Command> getSupplier() {
        return supplier;
    }
}