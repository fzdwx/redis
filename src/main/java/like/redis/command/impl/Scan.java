package like.redis.command.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import io.netty.channel.ChannelHandlerContext;
import like.redis.RedisCore;
import like.redis.command.Command;
import like.redis.command.CommandType;
import like.redis.protocal.Resp;
import like.redis.protocal.RespBulkStrings;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * command scan.
 * <p>
 * scan 0 Match * count 500
 * SCAN cursor [MATCH pattern] [COUNT count]
 *
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2022/1/3 18:12
 */
public class Scan implements Command {

    private int start;
    private String pattern;
    private int count;

    @Override
    public CommandType type() {
        return CommandType.scan;
    }

    @Override
    public void setContent(final Resp[] array) {
        final List<Resp> respList = ListUtil.of(array);
        final String command = CollUtil.join(CollUtil.map(respList, resp -> ((RespBulkStrings) resp).content().toString().toLowerCase(Locale.ROOT), true), " ");

        System.out.println(command);
    }

    @Override
    public void handle(final ChannelHandlerContext ctx, final RedisCore redisCore) {
        final LinkedList<Resp> list = CollUtil.newLinkedList();
        final RespBulkStrings nextCursor = RespBulkStrings.of("");
        list.addFirst(nextCursor);

        // TODO: 2022/1/3 scan

    }
}