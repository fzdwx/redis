package like.redis;

import io.netty.channel.Channel;
import like.redis.datatype.BytesWrapper;
import like.redis.datatype.RedisDataStructure;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2022/1/3 16:49
 */
public class RedisCoreImpl implements RedisCore {

    /**
     * 连接名:客户端
     */
    private final ConcurrentHashMap<BytesWrapper, Channel> clients = new ConcurrentHashMap<>();
    /**
     * 客户端:连接名
     */
    private final Map<Channel, BytesWrapper> clientNames = new ConcurrentHashMap<>();
    /**
     * db
     * <p>
     * key:value
     * todo 多数据库
     */
    private final ConcurrentSkipListMap<BytesWrapper, RedisDataStructure> DB = new ConcurrentSkipListMap<>();

    @Override
    public void saveClient(final Channel channel, final BytesWrapper connectionName) {
        clients.put(connectionName, channel);
        clientNames.put(channel, connectionName);
    }

    @Override
    public RedisDataStructure put(final BytesWrapper key, final RedisDataStructure redisData) {
        return db().put(key, redisData);
    }

    @Override
    public RedisDataStructure get(final BytesWrapper key) {
        // TODO: 2022/1/4 判断超时
        return db().get(key);
    }

    private ConcurrentSkipListMap<BytesWrapper, RedisDataStructure> db() {
        // TODO: 2022/1/4 切换db
        return DB;
    }
}