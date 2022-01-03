package like.redis;

import io.netty.channel.Channel;
import like.redis.datatype.BytesWrapper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2022/1/3 16:49
 */
public class RedisCoreImpl implements RedisCore {

    private final ConcurrentHashMap<BytesWrapper, Channel> clients = new ConcurrentHashMap<>();
    private final Map<Channel, BytesWrapper> clientNames = new ConcurrentHashMap<>();

    @Override
    public void put(final Channel channel, final BytesWrapper connectionName) {
        clients.put(connectionName, channel);
        clientNames.put(channel, connectionName);
    }
}