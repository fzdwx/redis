package like.redis;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
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

    /**
     * 设置客户端使用的db（0~15）
     *
     * @param channel 通道
     * @param dbNo    db 编号
     */
    private void setClientUseDb(final Channel channel, final int dbNo) {
        channel.attr(AttributeKey.valueOf(Common.ATTR_CONNECTION_DB_NO)).set(dbNo);
    }

    /**
     * 获取客户端使用的db（0~15）
     *
     * @param channel 通道
     * @return int 使用的db编号
     */
    private int getClientUseDb(final Channel channel) {
        final AttributeKey<Integer> attrKey = AttributeKey.valueOf(Common.ATTR_CONNECTION_DB_NO);
        final Integer dbNo = channel.attr(attrKey).get();
        if (dbNo == null) {
            channel.attr(attrKey).set(Common.DEFAULT_DB);
        }
        return channel.attr(attrKey).get();
    }

    /**
     * 设置客户端连接名
     *
     * @param channel        通道
     * @param connectionName 连接名
     */
    private void setClientName(final Channel channel, final BytesWrapper connectionName) {
        channel.attr(AttributeKey.valueOf(Common.ATTR_CONNECTION_CONNECTION_NAME)).set(connectionName);
    }
}