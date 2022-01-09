package like.redis.command.impl;

import cn.hutool.core.convert.Convert;
import io.netty.channel.ChannelHandlerContext;
import like.redis.server.RedisCore;
import like.redis.command.Command;
import like.redis.command.CommandType;
import like.redis.protocal.Resp;
import like.redis.protocal.RespErrors;
import like.redis.protocal.RespSimpleStrings;

/**
 * command select.
 * <p>
 * select index.
 *
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2022/1/4 17:20
 */
public class Select implements Command {

    private int dbNo;

    @Override
    public CommandType type() {
        return CommandType.select;
    }

    @Override
    public void setContent(final Resp[] array) {
        dbNo = Convert.toInt(Command.getContent(array, 1), 0);
    }

    @Override
    public void handle(final ChannelHandlerContext ctx, final RedisCore redisCore) {
        if (dbNo < 0 || dbNo > 15) {
            ctx.writeAndFlush(RespErrors.of("un Support databases number,must in [0 , 16)"));
        } else {
            redisCore.switchDb(ctx.channel(), dbNo);
            ctx.writeAndFlush(RespSimpleStrings.OK);
        }
    }
}
