package like.redis.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import like.redis.protocal.Resp;

/**
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2021/12/31 17:43
 */
public class ResponseEncoder extends MessageToByteEncoder<Resp> {

    @Override
    protected void encode(final ChannelHandlerContext ctx, final Resp resp, final ByteBuf out) throws Exception {
        try {
            Resp.write(resp, out);
            out.writeBytes(out);
        } catch (Exception e) {
            ctx.close();
        }
    }
}
