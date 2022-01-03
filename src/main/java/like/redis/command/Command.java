package like.redis.command;

import io.netty.channel.ChannelHandlerContext;
import like.redis.RedisCore;
import like.redis.datatype.BytesWrapper;
import like.redis.protocal.Resp;
import like.redis.protocal.RespArrays;
import like.redis.protocal.RespBulkStrings;
import like.redis.protocal.RespSimpleStrings;
import like.redis.util.LogUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2022/1/3 10:54
 */
public interface Command {

    /**
     * 获取接口类型
     *
     * @return 接口类型
     */
    CommandType type();

    /**
     * 注入属性
     *
     * @param array 操作数组
     */
    void setContent(Resp[] array);

    /**
     * 处理消息命令
     *
     * @param ctx       管道
     * @param redisCore redis数据库
     */
    void handle(ChannelHandlerContext ctx, RedisCore redisCore);

    Map<String, Supplier<Command>> commandMap = new HashMap<>() {
        {
            for (CommandType each : CommandType.values()) {
                put(each.name(), each.getSupplier());
            }
        }
    };

    /**
     * resp to command
     *
     * @param resp 分别地
     * @return {@link Command }
     */
    static Command from(Resp resp) {

        if (resp instanceof RespArrays arrays) {
            return from(arrays);
        } else if (resp instanceof RespSimpleStrings simpleStrings) {
            return from(simpleStrings);
        }

        LogUtil.error("客户端发送的命令应该只能是Resp Array 和 单行命令 类型，resp类型：{}", resp.getClass().toString());

        throw new IllegalStateException("客户端发送的命令应该只能是Resp Array 和 单行命令 类型，resp类型：" + resp.getClass().toString());
    }

    static String getContentStringFromArray(Resp[] array, int index) {
        return ((RespBulkStrings) array[index]).content().toString().toLowerCase();
    }

    static BytesWrapper getContentFromArray(Resp[] array, int index) {
        return ((RespBulkStrings) array[index]).content();
    }

    private static Command from(RespArrays arrays) {
        final Resp[] respArrays = arrays.array();

        final String commandName = getContentStringFromArray(respArrays, 0);

        final Supplier<Command> commandSupplier = commandMap.get(commandName);
        if (commandSupplier == null) {
            LogUtil.error("不支持的命令 [{}]", commandName);
        } else {
            try {
                final Command command = commandSupplier.get();
                command.setContent(respArrays);
                return command;
            } catch (Exception e) {
                LogUtil.error("不支持的命令 [{}]", commandName, e);
            }
        }
        return null;
    }

    private static Command from(RespSimpleStrings simpleStrings) {
        final String commandName = simpleStrings.content().toLowerCase(Locale.ROOT);

        final Supplier<Command> commandSupplier = commandMap.get(commandName);
        if (commandSupplier == null) {
            LogUtil.error("不支持的命令 [{}]", commandName);
        } else {
            try {
                return commandSupplier.get();
            } catch (Exception e) {
                LogUtil.error("不支持的命令 [{}]", commandName, e);
            }
        }
        return null;
    }
}