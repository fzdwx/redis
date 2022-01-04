package like.redis.command.impl.string;

import io.netty.channel.ChannelHandlerContext;
import like.redis.RedisCore;
import like.redis.command.Command;
import like.redis.command.CommandType;
import like.redis.datatype.BytesWrapper;
import like.redis.datatype.RedisString;
import like.redis.protocal.Resp;
import like.redis.protocal.RespBulkStrings;
import like.redis.protocal.RespSimpleStrings;

/**
 * command set.
 * <p>
 * set key value [expiration EX seconds|PX milliseconds] [NX|XX]
 *
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2022/1/4 14:05
 */
public class Set implements Command {

    private BytesWrapper key;
    private BytesWrapper value;

    @Override
    public CommandType type() {
        return CommandType.set;
    }

    @Override
    public void setContent(final Resp[] array) {
        key = Command.getBytesWrapper(array, 1);
        value = Command.getBytesWrapper(array, 2);

        // TODO: 2022/1/4 ex px nx xx
    }

    @Override
    public void handle(final ChannelHandlerContext ctx, final RedisCore redisCore) {
        final var oldData = redisCore.put(key, RedisString.create(value));
        if (oldData instanceof RedisString s) {
            ctx.writeAndFlush(RespBulkStrings.of(s.value()));
        } else {
            ctx.writeAndFlush(RespSimpleStrings.OK);
        }
    }
}