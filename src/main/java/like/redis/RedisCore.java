package like.redis;

import io.netty.channel.Channel;
import like.redis.datatype.BytesWrapper;

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
     * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
     * @date 2022/01/03 17:26:44
     */
    void put(Channel channel, BytesWrapper content);
}