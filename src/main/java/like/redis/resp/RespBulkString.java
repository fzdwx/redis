package like.redis.resp;

import like.redis.datatype.BytesWrapper;
import lombok.Getter;

/**
 * 批量字符串
 *
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2021/12/31 17:51
 */
public record RespBulkString(@Getter BytesWrapper content) implements Resp {

    public static final RespBulkString NULL_BULK_STRING = new RespBulkString(null);

}