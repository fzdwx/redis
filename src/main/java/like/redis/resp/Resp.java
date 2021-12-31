package like.redis.resp;

import io.netty.buffer.ByteBuf;

/**
 * redis 客户端和服务端之间通信的协议是RESP（REdis Serialization Protocol）
 *
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2021/12/31 17:44
 */
public interface Resp {

    /**
     * 将{@link Resp} 写到客户端
     *
     * @param resp 服务端响应
     * @param out  客户端入口buf
     */
    static void write(final Resp resp, final ByteBuf out) {

        if (resp instanceof RespSimpleString s) {

            normal(out);

            final char[] charArray = s.content().toCharArray();
            for (final char c : charArray) {
                out.writeByte((byte) c);
            }

            newLine(out);
        } else if (resp instanceof RespErrors e) {

            error(out);

            final char[] charArray = e.content().toCharArray();
            for (final char c : charArray) {
                out.writeByte((byte) c);
            }

            newLine(out);
        }

    }


    static void newLine(ByteBuf out) {
        out.writeByte((byte) '\r');
        out.writeByte((byte) '\n');
    }

    /**
     * (+) 表示一个正确的状态信息
     */
    static void normal(ByteBuf out) {
        out.writeByte((byte) '+');
    }

    /**
     * (-) 表示一个错误信息
     */
    static void error(ByteBuf out) {
        out.writeByte((byte) '-');
    }

    /**
     * (:) 表示一个整数信息
     */
    static void integer(ByteBuf out) {
        out.writeByte((byte) ':');
    }

    /**
     * ($) 表示一个批量字符串信息
     */
    static void bulkString(ByteBuf out) {
        out.writeByte((byte) '$');
    }

    /**
     * (*) 表示一个数组信息
     */
    static void array(ByteBuf out) {
        out.writeByte((byte) '*');
    }
}