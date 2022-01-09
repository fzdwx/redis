package like.redis;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.EventExecutorGroup;
import like.redis.channel.RedisOption;
import like.redis.channel.single.DefaultRedisOption;
import like.redis.codec.RespDecoder;
import like.redis.codec.RespEncoder;
import like.redis.command.RedisCommandHandler;
import like.redis.server.RedisCore;
import like.redis.server.RedisServer;
import like.redis.util.LogUtil;
import like.redis.util.PropertiesUtil;

import java.net.InetSocketAddress;

/**
 * redis server 默认实现
 *
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2021/12/31 17:38
 */
public class LikeRedisServer implements RedisServer {

    private final RedisOption<ServerChannel> channelOption;
    private final EventExecutorGroup redisSingleEventExecutor;
    private final RedisCore redisCore;
    private final ServerBootstrap serverBootstrap = new ServerBootstrap();

    public LikeRedisServer(final RedisOption<ServerChannel> redisOption) {
        this.channelOption = redisOption;
        this.redisCore = redisOption.redisCore();
        this.redisSingleEventExecutor = new NioEventLoopGroup(1);
    }

    public static void main(String[] args) {
        final LikeRedisServer redisServer = new LikeRedisServer(DefaultRedisOption.create());
        redisServer.start();
    }

    @Override
    public void start() {
        serverBootstrap.group(channelOption.boos(), channelOption.selectors())
                .channel(channelOption.getChannelClass())
                .handler(new LoggingHandler(LogLevel.INFO))
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.SO_KEEPALIVE, PropertiesUtil.getTcpKeepAlive())
                .localAddress(new InetSocketAddress(PropertiesUtil.getNodeAddress(), PropertiesUtil.getNodePort()))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline channelPipeline = socketChannel.pipeline();
                        channelPipeline.addLast(
                                // 发送到客户端 command => response
                                new RespEncoder(),
                                // 接收客户端 request => command
                                new RespDecoder(),
                                // heart beat
                                new IdleStateHandler(0, 0, 20)
                        );
                        // handle command
                        channelPipeline.addLast(redisSingleEventExecutor, new RedisCommandHandler(redisCore));
                    }
                });
        try {
            final ChannelFuture sync = serverBootstrap.bind().sync();
            LogUtil.info("start success! local address : {}", sync.channel().localAddress().toString());
        } catch (InterruptedException e) {
            LogUtil.warn("interrupted ", e);
        }
    }

    @Override
    public void close() {
        channelOption.boos().shutdownGracefully();
        channelOption.selectors().shutdownGracefully();
        redisSingleEventExecutor.shutdownGracefully();
    }
}
