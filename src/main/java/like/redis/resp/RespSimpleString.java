package like.redis.resp;

import lombok.Getter;

/**
 * 简单字符串
 *
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2021/12/31 18:01
 */
public record RespSimpleString(@Getter String content) implements Resp {

    public static final RespSimpleString OK = new RespSimpleString("OK");

}