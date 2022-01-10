package like.redis.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import like.redis.protocal.Resp;
import like.redis.util.LogUtil;

import java.util.function.Consumer;

/**
 * resp协议客户端响应解码器
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2022/1/9 14:13
 */
public class RespClientDecoder extends LengthFieldBasedFrameDecoder {

    private static final int MAX_FRAME_LENGTH = Integer.MAX_VALUE;

    private final Consumer<Resp> respConsumer;

    public RespClientDecoder(Consumer<Resp> respConsumer) {
        super(MAX_FRAME_LENGTH, 0, 4);
        this.respConsumer = respConsumer;
    }

    @Override
    protected Object decode(final ChannelHandlerContext ctx, final ByteBuf in) throws Exception {
        while (in.readableBytes() != 0) {
            int mark = in.readerIndex();
            try {
                Resp resp = Resp.decodeForClient(in);

                respConsumer.accept(resp);

            } catch (Exception e) {
                in.readerIndex(mark);
                LogUtil.error("解码命令失败", e);
            }
        }
        return null;
    }
}