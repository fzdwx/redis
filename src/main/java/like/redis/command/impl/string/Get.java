package like.redis.command.impl.string;

import io.netty.channel.ChannelHandlerContext;
import like.redis.server.RedisCore;
import like.redis.command.Command;
import like.redis.command.CommandType;
import like.redis.datatype.BytesWrapper;
import like.redis.datatype.RedisString;
import like.redis.protocal.Resp;
import like.redis.protocal.RespBulkStrings;
import like.redis.protocal.RespErrors;

/**
 * command get.
 * <p>
 * get key
 *
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2022/1/4 14:50
 */
public class Get implements Command {

    private BytesWrapper key;

    @Override
    public CommandType type() {
        return CommandType.get;
    }

    @Override
    public void setContent(final Resp[] array) {
        this.key = Command.getBytesWrapper(array, 1);
    }

    @Override
    public void handle(final ChannelHandlerContext ctx, final RedisCore redisCore) {
        final var data = redisCore.get(ctx.channel(),key);

        if (data == null) {
            ctx.writeAndFlush(RespBulkStrings.NULL_BULK_STRING);
        } else if (data instanceof RedisString s) {
            ctx.writeAndFlush(RespBulkStrings.of(s.value()));
        } else {
            ctx.writeAndFlush(RespErrors.of("this value is not string"));
        }

    }
}
