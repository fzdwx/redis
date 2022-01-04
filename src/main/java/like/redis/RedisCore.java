package like.redis;

import io.netty.channel.Channel;
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
}