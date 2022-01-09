package like.redis.command.impl;

import io.netty.channel.ChannelHandlerContext;
import like.redis.server.RedisCore;
import like.redis.command.Command;
import like.redis.command.CommandConstants;
import like.redis.command.CommandType;
import like.redis.protocal.*;
import like.redis.util.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * command config.
 * <p>
 * config get databases
 *
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2022/1/3 17:44
 */
public class Config implements Command {

    final static int ARRAY_LENGTH = 3;
    private String subCommand;

    @Override
    public CommandType type() {
        return CommandType.config;
    }

    @Override
    public void setContent(final Resp[] array) {
        if (array.length != ARRAY_LENGTH) {
            throw new IllegalStateException(Arrays.toString(array));
        }

        if (!Command.getContent(array, 1).equals(CommandConstants.GET)) {
            throw new IllegalStateException(Arrays.toString(array));
        }

        this.subCommand = Command.getContent(array, 2);
    }

    @Override
    public void handle(final ChannelHandlerContext ctx, final RedisCore redisCore) {
        if (subCommand.equals(CommandConstants.DATABASES)) {
            final List<Resp> list = new ArrayList<>();

            list.add(RespBulkStrings.of("databases"));
            list.add(RespBulkStrings.of("16"));

            ctx.writeAndFlush(RespArrays.of(list));
        } else if (subCommand.equals(CommandConstants.DB)) {
            ctx.writeAndFlush(RespIntegers.of(redisCore.getClientUseDb(ctx.channel())));
        } else {
            ctx.writeAndFlush(RespErrors.of("un Support command [config]: " + subCommand));
            LogUtil.error("未识别的config命令:" + subCommand);

            ctx.channel().close();
        }
    }
}
