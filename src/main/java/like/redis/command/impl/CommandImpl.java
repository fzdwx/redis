package like.redis.command.impl;

import io.netty.channel.ChannelHandlerContext;
import like.redis.server.RedisCore;
import like.redis.command.Command;
import like.redis.command.CommandType;
import like.redis.protocal.Resp;

/**
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2022/1/4 10:42
 */
public class CommandImpl implements Command {

    @Override
    public CommandType type() {
        return null;
    }

    @Override
    public void setContent(final Resp[] array) {

    }

    @Override
    public void handle(final ChannelHandlerContext ctx, final RedisCore redisCore) {

    }
}
