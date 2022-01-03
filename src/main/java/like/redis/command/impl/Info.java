package like.redis.command.impl;

import cn.hutool.core.collection.CollUtil;
import io.netty.channel.ChannelHandlerContext;
import like.redis.RedisCore;
import like.redis.command.Command;
import like.redis.command.CommandType;
import like.redis.common.Common;
import like.redis.protocal.Resp;
import like.redis.protocal.RespBulkStrings;

import java.util.List;

import static cn.hutool.core.util.RuntimeUtil.getPid;

/**
 * command info.
 *
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2022/1/3 18:00
 */
public class Info implements Command {

    @Override
    public CommandType type() {
        return CommandType.info;
    }

    @Override
    public void setContent(final Resp[] array) {

    }

    @Override
    public void handle(final ChannelHandlerContext ctx, final RedisCore redisCore) {
        final List<String> list = CollUtil.newArrayList();

        list.add("redis_version:" + Common.REDIS_VERSION);
        list.add("os:" + System.getProperty(Common.OS_NAME));
        list.add("process_id:" + getPid());

        ctx.writeAndFlush(RespBulkStrings.of(list));
    }
}