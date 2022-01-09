package like.redis.command;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import like.redis.server.RedisCore;
import like.redis.util.LogUtil;

/**
 * command handler.
 *
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2022/1/3 16:32
 */
public class RedisCommandHandler extends SimpleChannelInboundHandler<Command> {

    private final RedisCore redisCore;

    public RedisCommandHandler(final RedisCore redisCore) {
        this.redisCore = redisCore;
    }

    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, final Command command) throws Exception {
        LogUtil.debug("start process command [{}]", command.type().name());

        try {
            command.handle(ctx, redisCore);
        } catch (Exception e) {
            LogUtil.error("process fail command [{}]", command.type().name(), e);
        }

        LogUtil.debug("process finish [{}]", command.type().name());
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
        LogUtil.error("redis command handle ", cause);
    }

    @Override
    public void channelUnregistered(final ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        ctx.close();
    }
}
