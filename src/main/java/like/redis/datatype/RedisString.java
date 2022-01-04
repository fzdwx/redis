package like.redis.datatype;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2022/1/4 14:35
 */
@Data
@Accessors(fluent = true)
public class RedisString implements RedisDataStructure {

    private BytesWrapper value;
    private volatile long timeout = -1;

    @Override
    public void timeout(final long timeout) {
        this.timeout = timeout;
    }

    @Override
    public long timeout() {
        return timeout;
    }

    private RedisString(final BytesWrapper value) {
        this.value = value;
    }

    public static RedisString create(BytesWrapper value) {
        return new RedisString(value);
    }
}