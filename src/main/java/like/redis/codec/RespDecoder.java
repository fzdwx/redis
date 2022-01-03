package like.redis.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import like.redis.command.Command;
import like.redis.protocal.Resp;
import like.redis.protocal.RespArrays;
import like.redis.protocal.RespBulkStrings;
import like.redis.protocal.RespErrors;
import like.redis.util.LogUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2022/1/3 10:39
 */
@Slf4j
public class RespDecoder extends LengthFieldBasedFrameDecoder {

    private static final int MAX_FRAME_LENGTH = Integer.MAX_VALUE;

    public RespDecoder() {
        super(MAX_FRAME_LENGTH, 0, 4);
    }

    @Override
    protected Object decode(final ChannelHandlerContext ctx, final ByteBuf in) throws Exception {
        while (in.readableBytes() != 0) {
            int mark = in.readerIndex();
            try {
                Resp resp = Resp.decode(in);

                Command command = Command.from(resp);
                if (command == null) {
                    ctx.writeAndFlush(new RespErrors("unSupport command:" + ((RespBulkStrings) ((RespArrays) resp).array()[0]).content().toString()));
                } else {
                    // TODO: 2022/1/3 aof
                    return command;
                }
            } catch (Exception e) {
                in.readerIndex(mark);
                LogUtil.error("解码命令失败", e);
            }
        }
        return null;
    }
}