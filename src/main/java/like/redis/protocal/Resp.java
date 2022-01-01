package like.redis.protocal;

import io.netty.buffer.ByteBuf;

import java.util.Arrays;
import java.util.Objects;

/**
 * redis 客户端和服务端之间通信的协议是RESP（REdis Serialization Protocol）
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2021/12/31 17:44
 */
public interface Resp {

    /**
     * 将{@link Resp} 写到客户端
     * @param resp 服务端响应
     * @param out  客户端入口buf
     */
    static void write(final Resp resp, final ByteBuf out) {

        switch (resp) {
            case RespSimpleStrings s -> {

                handleSimpleStrings(out, s);

            }
            case RespErrors e -> {

                handleErrors(out, e);

            }
            case RespIntegers i -> {

                handleIntegers(out, i);

            }
            case RespBulkStrings s -> {

                handleBulkStrings(out, s);

            }
            case RespArrays arrays -> {

                handleArrays(out, arrays);

            }
            default -> {
                throw new IllegalArgumentException("dismiss find resp handle :" + resp.getClass());
            }
        }

    }

    /**
     * 总是以 "\r\n" (CRLF) 结束
     */
   private static void newLine(ByteBuf out) {
        out.writeByte(( byte ) '\r');
        out.writeByte(( byte ) '\n');
    }

    /**
     * (+) 表示一个正确的状态信息
     */
    private static void normal(ByteBuf out) {
        out.writeByte(( byte ) '+');
    }

    /**
     * (-) 表示一个错误信息
     */
    private static void error(ByteBuf out) {
        out.writeByte(( byte ) '-');
    }

    /**
     * (:) 表示一个整数信息
     */
    private static void integer(ByteBuf out) {
        out.writeByte(( byte ) ':');
    }

    /**
     * ($) 表示一个批量字符串信息
     */
    private static void bulkString(ByteBuf out) {
        out.writeByte(( byte ) '$');
    }

    /**
     * (*) 表示一个数组信息
     */
    private static void array(ByteBuf out) {
        out.writeByte(( byte ) '*');
    }

    private static void handleIntegers(final ByteBuf out, final RespIntegers i) {
        integer(out);

        foreachAndWrite(out, String.valueOf(i.value()));

        newLine(out);
    }

    private static void handleErrors(final ByteBuf out, final RespErrors e) {
        error(out);

        foreachAndWrite(out, e.content());

        newLine(out);
    }

    private static void handleSimpleStrings(final ByteBuf out, final RespSimpleStrings s) {
        normal(out);

        foreachAndWrite(out, s.content());

        newLine(out);
    }

    private static void handleArrays(final ByteBuf out, final RespArrays arrays) {
        array(out);

        final var array = arrays.array();
        foreachAndWrite(out, String.valueOf(array.length));
        newLine(out);

        Arrays.stream(array).forEach(r -> write(r, out));
    }

    private static void handleBulkStrings(final ByteBuf out, final RespBulkStrings s) {
        bulkString(out);

        final var bytesWrapper = s.content();
        // null
        if (Objects.isNull(bytesWrapper)) {
            error(out);
            out.writeByte(( byte ) '1');
            newLine(out);

        } else
            // 空值
            if (bytesWrapper.getContent().length == 0) {

                out.writeByte(( byte ) '0');
                newLine(out);
                newLine(out);

            } else {

                foreachAndWrite(out, String.valueOf(bytesWrapper.getContent().length));
                newLine(out);
                out.writeBytes(bytesWrapper.getContent());
                newLine(out);
            }
    }

    private static void foreachAndWrite(final ByteBuf out, final String content) {
        final var charArray = content.toCharArray();
        for (final char c : charArray) {
            out.writeByte(( byte ) c);
        }
    }
}
