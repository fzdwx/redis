package like.redis.channel.single;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import like.redis.RedisCore;
import like.redis.RedisCoreImpl;
import like.redis.channel.RedisOption;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2022/1/3 11:29
 */
public class DefaultRedisOption implements RedisOption<ServerChannel> {

    private final NioEventLoopGroup single;
    private final RedisCore redisCore;

    public static DefaultRedisOption create() {
        return new DefaultRedisOption();
    }

    private DefaultRedisOption() {
        this.single = new NioEventLoopGroup(new ThreadFactory() {

            final AtomicInteger index = new AtomicInteger(0);

            @Override
            public Thread newThread(final Runnable r) {
                return new Thread(r, "redis_boss_" + index.getAndIncrement());
            }
        });
        this.redisCore = new RedisCoreImpl();
    }

    @Override
    public RedisCore redisCore() {
        return this.redisCore;
    }

    @Override
    public EventLoopGroup boos() {
        return single;
    }

    @Override
    public EventLoopGroup selectors() {
        return single;
    }

    @Override
    public Class<NioServerSocketChannel> getChannelClass() {
        return NioServerSocketChannel.class;
    }
}