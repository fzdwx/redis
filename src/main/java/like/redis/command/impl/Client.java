package like.redis.command.impl;

import io.netty.channel.ChannelHandlerContext;
import like.redis.RedisCore;
import like.redis.command.Command;
import like.redis.command.CommandConstants;
import like.redis.command.CommandType;
import like.redis.protocal.Resp;
import like.redis.protocal.RespSimpleStrings;
import like.redis.util.LogUtil;

import java.util.Objects;

/**
 * command client.
 * <p>
 * 保存客户端连接
 *
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2022/1/3 17:14
 */
public class Client implements Command {

    private Resp[] array;
    private String subCommand;

    @Override
    public CommandType type() {
        return CommandType.client;
    }

    @Override
    public void setContent(final Resp[] array) {
        this.array = array;
        this.subCommand = Command.getContentFromArray(array, 1);
    }

    @Override
    public void handle(final ChannelHandlerContext ctx, final RedisCore redisCore) {

        if (Objects.equals(subCommand, CommandConstants.SET_NAME)) {

            final String connectionName = Command.getContentFromArray(array, 2);

            redisCore.addClient(connectionName, ctx.channel());

        } else {
            LogUtil.error("un Support command [{}]", subCommand);
        }

        ctx.writeAndFlush(RespSimpleStrings.OK);
    }
}