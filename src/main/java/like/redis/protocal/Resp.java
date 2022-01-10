package like.redis.protocal;

import io.netty.buffer.ByteBuf;
import like.redis.datatype.BytesWrapper;

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
                throw new IllegalArgumentException("[Redis server ]|- dismiss find resp handle :" + resp.getClass());
            }
        }

    }

    static Resp decode(ByteBuf in) {
        if (in.readableBytes() <= 0) {
            throw new IllegalStateException("[Redis server ]|- 没有读取到完整的命令");
        }
        final char c = ( char ) in.readByte();
        if (c == '+') {
            return new RespSimpleStrings(getContent(in));
        } else if (c == '-') {
            return new RespErrors(getContent(in));
        } else if (c == ':') {
            return new RespIntegers(getNumber(in));
        } else if (c == '$') {
            final int length = getNumber(in);
            byte[] content;
            if (length == -1) {
                return RespBulkStrings.NULL_BULK_STRING;
            } else {
                // if (in.readableBytes() < length + 2) {
                //     throw new IllegalStateException("[Redis server ]|- 没有读取到完整的命令");
                // }
                // todo bug
                content = new byte[length];
                in.readBytes(content);
            }
            if (in.readByte() != '\r' || in.readByte() != '\n') {
                throw new IllegalStateException("[Redis server ]|- 没有读取到完整的命令");
            }
            return new RespBulkStrings(new BytesWrapper(content));
        } else if (c == '*') {
            int numOfElement = getNumber(in);
            Resp[] array = new Resp[numOfElement];
            for (int i = 0; i < numOfElement; i++) {
                array[i] = decode(in);
            }
            return new RespArrays(array);
        } else {
            if (c > 64 && c < 91) {
                return new RespSimpleStrings(c + getContent(in));
            } else {
                return decode(in);
            }
        }
    }

    static Resp decodeForClient(ByteBuf in) {
        if (in.readableBytes() <= 0) {
            throw new IllegalStateException("[Redis server ]|- 没有读取到完整的命令");
        }
        final char c = ( char ) in.readByte();
        if (c == '+') {
            return new RespSimpleStrings(getContent(in));
        } else if (c == '-') {
            return new RespErrors(getContent(in));
        } else if (c == ':') {
            return new RespIntegers(getNumber(in));
        } else if (c == '$') {
            final int length = getNumber(in);
            byte[] content;
            if (length == -1) {
                return RespBulkStrings.NULL_BULK_STRING;
            } else {
                if (in.readableBytes() < length + 2) {
                    throw new IllegalStateException("[Redis server ]|- 没有读取到完整的命令");
                }
                content = new byte[length];
                in.readBytes(content);
            }
            if (in.readByte() != '\r' || in.readByte() != '\n') {
                throw new IllegalStateException("[Redis server ]|- 没有读取到完整的命令");
            }
            return new RespBulkStrings(new BytesWrapper(content));
        } else if (c == '*') {
            int numOfElement = getNumber(in);
            Resp[] array = new Resp[numOfElement];
            for (int i = 0; i < numOfElement; i++) {
                array[i] = decode(in);
            }
            return new RespArrays(array);
        } else {
            if (c > 64 && c < 91) {
                return new RespSimpleStrings(c + getContent(in));
            } else {
                return decode(in);
            }
        }
    }

    private static String getContent(ByteBuf in) {
        char c;
        StringBuilder builder = new StringBuilder();
        while (in.readableBytes() > 0 && (c = ( char ) in.readByte()) != '\r') {
            builder.append(c);
        }
        if (in.readableBytes() == 0 || in.readByte() != '\n') {
            throw new IllegalStateException("[Redis server ]|- 没有读取到完整的命令");
        }
        return builder.toString();
    }

    private static int getNumber(ByteBuf buffer) {
        char t;
        t = ( char ) buffer.readByte();
        boolean positive = true;
        int value = 0;
        // 错误（Errors）： 响应的首字节是 "-"
        if (t == '-') {
            positive = false;
        } else {
            value = t - '0';
        }
        while (buffer.readableBytes() > 0 && (t = ( char ) buffer.readByte()) != '\r') {
            value = value * 10 + (t - '0');
        }
        if (buffer.readableBytes() == 0 || buffer.readByte() != '\n') {
            throw new IllegalStateException("[Redis server ]|- 没有读取到完整的命令");
        }
        if (!positive) {
            value = -value;
        }
        return value;
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
            if (bytesWrapper.content().length == 0) {

                out.writeByte(( byte ) '0');
                newLine(out);
                newLine(out);

            } else {

                foreachAndWrite(out, String.valueOf(bytesWrapper.content().length));
                newLine(out);
                out.writeBytes(bytesWrapper.content());
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