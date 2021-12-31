package like.redis.resp;

import lombok.Getter;

/**
 * 数组
 *
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2021/12/31 17:59
 */
public record RespArray(@Getter Resp[] array) implements Resp {

}