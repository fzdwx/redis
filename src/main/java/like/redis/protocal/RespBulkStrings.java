package like.redis.protocal;

import like.redis.common.Common;
import like.redis.datatype.BytesWrapper;
import lombok.Getter;

import java.util.List;

/**
 * 多行字符串
 * <p>
 * 多行字符串被用来表示最大512MB长的二进制安全字符串。
 * 多行字符串编码方式：<br>
 * <p>
 * 1.美元符 "\$" 后面跟着组成字符串的字节数(前缀长度)，并以 CRLF 结尾。<br>
 * 2.实际的字符串数据。<br>
 * 3.结尾是 CRLF。<br>
 * <p>
 * 所以，字符串 "foobar" 编码如下: {@code "$6\r\nfoobar\r\n"};空字符串编码格式：{@code "$0\r\n\r\n"}
 * <p>
 * 也可以使用一个特殊的用来表示空值的格式表示不存在的值。在这种格式里长度值为-1，数据部分不存在，所以空（Null）用如下方式表示：{@code "$-1\r\n"}
 *
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2021/12/31 17:51
 */
public record RespBulkStrings(@Getter BytesWrapper content) implements Resp {

    public static final RespBulkStrings NULL_BULK_STRING = new RespBulkStrings(null);

    public static RespBulkStrings of(final BytesWrapper content) {
        return new RespBulkStrings(content);
    }

    public static RespBulkStrings of(final String content) {
        return of(new BytesWrapper(content.getBytes(Common.UTF8)));
    }

    public static RespBulkStrings of(final List<String> contents) {
        final String s = contents.stream().map(name -> name + "\r\n").reduce((first, second) -> first + second).get();
        return of(s);
    }
}