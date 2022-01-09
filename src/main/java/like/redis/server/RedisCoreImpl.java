package like.redis.server;

import io.netty.channel.Channel;
import like.redis.common.Common;
import like.redis.datatype.BytesWrapper;
import like.redis.datatype.RedisDataStructure;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.IntStream;

/**
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2022/1/3 16:49
 */
public class RedisCoreImpl implements RedisCore {

    /**
     * id:channel
     */
    private final Map<String, Channel> clients = new ConcurrentHashMap<>();
    /**
     * dbs
     */
    private static final List<ConcurrentSkipListMap<BytesWrapper, RedisDataStructure>> DBS = new LinkedList<>();

    static {
        IntStream.range(0, 16).forEach(i -> {
            DBS.add(new ConcurrentSkipListMap<>());
        });
    }

    @Override
    public void saveClient(final Channel channel, final BytesWrapper connectionName) {
        setClientName(channel, connectionName);

        setClientUseDb(channel, Common.DEFAULT_DB);

        clients.putIfAbsent(channel.id().asLongText(), channel);
    }

    @Override
    public void switchDb(final Channel channel, final int dbNo) {
        setClientUseDb(channel, dbNo);
    }

    @Override
    public RedisDataStructure put(final Channel channel, final BytesWrapper key, final RedisDataStructure redisData) {
        return currentDb(channel).put(key, redisData);
    }

    @Override
    public RedisDataStructure get(final Channel channel, final BytesWrapper key) {
        // TODO: 2022/1/4 判断超时
        return currentDb(channel).get(key);
    }

    /**
     * 当前client的数据库
     *
     * @param channel 通道
     * @return {@link ConcurrentSkipListMap<BytesWrapper, RedisDataStructure> }
     */
    private ConcurrentSkipListMap<BytesWrapper, RedisDataStructure> currentDb(final Channel channel) {
        return DBS.get(getClientUseDb(channel));
    }
}
