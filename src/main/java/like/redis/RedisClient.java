package like.redis;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import like.redis.client.CommandSender;
import like.redis.client.RespClientDecoder;
import like.redis.codec.RespEncoder;
import like.redis.util.PropertiesUtil;
import lombok.SneakyThrows;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2022/1/9 13:17
 */
public class RedisClient {

    final static Bootstrap client = new Bootstrap();

    final static NioEventLoopGroup clientEventGroup = new NioEventLoopGroup(2, new ThreadFactory() {

        final AtomicInteger index = new AtomicInteger(0);

        @Override
        public Thread newThread(final Runnable r) {
            return new Thread(r, "redis_client_" + index.getAndIncrement());
        }
    });

    @SneakyThrows
    public static void main(String[] args) {
        client.group(clientEventGroup);
        client.channel(NioSocketChannel.class);
        client.handler(new LoggingHandler(LogLevel.INFO));
        client.option(ChannelOption.SO_REUSEADDR, true);
        client.option(ChannelOption.SO_KEEPALIVE, PropertiesUtil.getTcpKeepAlive());
        client.localAddress(7777);
        client.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(final NioSocketChannel ch) throws Exception {
                final var pipeline = ch.pipeline();
                pipeline.addLast(
                        new RespClientDecoder(CommandSender.respConsumer()),
                        new RespEncoder(),
                        new IdleStateHandler(0, 0, 20)
                );
                // 命令发送器
                clientEventGroup.submit(CommandSender.create(pipeline.channel()));
            }
        });

        client.connect("localhost", 6379).sync();

    }

}
