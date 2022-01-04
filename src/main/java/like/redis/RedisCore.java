package like.redis;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import like.redis.common.Common;
import like.redis.datatype.BytesWrapper;
import like.redis.datatype.RedisDataStructure;

/**
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2022/1/3 10:56
 */
public interface RedisCore {

    /**
     * 添加客户端
     *
     * @param content 连接名
     * @param channel 通道
     */
    void saveClient(Channel channel, BytesWrapper content);

    /**
     * 切换数据库
     *
     * @param channel 通道
     * @param dbNo    数据库编号
     */
    void switchDb(Channel channel, int dbNo);

    /**
     * 存放数据
     *
     * @param channel
     * @param key       关键
     * @param redisData redis 数据结构
     * @return 旧值（如果不是第一次put）
     */
    RedisDataStructure put(final Channel channel, BytesWrapper key, RedisDataStructure redisData);

    /**
     * 获取数据
     *
     * @param channel
     * @param key     key
     * @return {@link RedisDataStructure }
     */
    RedisDataStructure get(final Channel channel, BytesWrapper key);

    /**
     * 设置客户端使用的db（0~15）
     *
     * @param channel 通道
     * @param dbNo    db 编号
     */
    default void setClientUseDb(final Channel channel, final int dbNo) {
        channel.attr(AttributeKey.valueOf(Common.ATTR_CONNECTION_DB_NO)).set(dbNo);
    }

    /**
     * 获取客户端使用的db（0~15）
     *
     * @param channel 通道
     * @return int 使用的db编号
     */

    default int getClientUseDb(final Channel channel) {
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
    default void setClientName(final Channel channel, final BytesWrapper connectionName) {
        channel.attr(AttributeKey.valueOf(Common.ATTR_CONNECTION_CONNECTION_NAME)).set(connectionName);
    }
}