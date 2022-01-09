package like.redis.channel;

import io.netty.channel.EventLoopGroup;

import io.netty.channel.Channel;
import like.redis.server.RedisCore;

/**
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2022/1/3 11:30
 */
public interface RedisOption<C extends Channel> {

    RedisCore redisCore();

    EventLoopGroup boos();

    EventLoopGroup selectors();

    Class<? extends C> getChannelClass();
}
