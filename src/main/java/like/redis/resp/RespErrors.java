package like.redis.resp;

import lombok.Getter;

/**
 * 错误
 *
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2021/12/31 17:58
 */
public record RespErrors(@Getter String content) implements Resp {

}