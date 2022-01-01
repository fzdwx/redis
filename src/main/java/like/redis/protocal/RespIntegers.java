package like.redis.protocal;

import lombok.Getter;

/**
 * 整型
 * <p>
 * 整数类型是由以冒号开头，CRLF结尾，中间是字符串形式表示的数字。 例如 {@code ":0\r\n"}, 或 {@code ":1000\r\n" }都是整数回复。
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2021/12/31 18:01
 */
public record RespIntegers(@Getter int value) implements Resp {

}
