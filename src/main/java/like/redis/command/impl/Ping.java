package like.redis.command.impl;

import io.netty.channel.ChannelHandlerContext;
import like.redis.RedisCore;
import like.redis.command.Command;
import like.redis.command.CommandType;
import like.redis.protocal.Resp;
import like.redis.protocal.RespSimpleStrings;

/**
 * command ping.
 * <p>
 * ping pong
 *
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2022/1/3 16:47
 */
public class Ping implements Command {

    @Override
    public CommandType type() {
        return CommandType.ping;
    }

    @Override
    public void setContent(final Resp[] array) {
        // ..
    }

    @Override
    public void handle(final ChannelHandlerContext ctx, final RedisCore redisCore) {
        ctx.writeAndFlush(RespSimpleStrings.of("PONG"));
    }
}