package like.redis.protocal;

import lombok.Getter;

/**
 * 错误
 * <p>
 * 错误被客户端当作异常处理，组成错误类型的字符串是错误消息自身,基本格式如下:{@code "-Error message\r\n"}
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2021/12/31 17:58
 */
public record RespErrors(@Getter String content) implements Resp {

    public static RespErrors of(String content) {
        return new RespErrors(content);
    }
}