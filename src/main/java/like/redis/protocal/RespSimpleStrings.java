package like.redis.protocal;

import lombok.Getter;

/**
 * 单行字符串
 * <p>
 * 许多redis命令在成功时回复"OK"，即简单字符串用以下5个字节编码：{@code "+OK\r\n"}
 *
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2021/12/31 18:01
 */
public record RespSimpleStrings(@Getter String content) implements Resp {

    public static final RespSimpleStrings OK = new RespSimpleStrings("OK");

    public static RespSimpleStrings of(final String content) {
        return new RespSimpleStrings(content);
    }
}