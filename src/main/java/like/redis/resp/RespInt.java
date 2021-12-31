package like.redis.resp;

import lombok.Getter;

/**
 * 整数
 *
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2021/12/31 18:01
 */
public record RespInt(@Getter int value) implements Resp {

}