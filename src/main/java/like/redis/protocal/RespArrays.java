package like.redis.protocal;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import lombok.Getter;

import java.util.List;
import java.util.Locale;

/**
 * 数组
 * <p>
 * RESP 数组使用如下格式发送：
 * <p>
 * 以星号* 为首字符，接着是表示数组中元素个数的十进制数，最后以 CRLF 结尾。<br>
 * 外加数组中每个 RESP 类型的元素。
 * <p>
 * 数组可以包含混合类型，不一定必须是同一种类型。例如，4个整型和1个多行字符串编码方式：
 * <p>
 * *5\r\n<br>
 * :1\r\n<br>
 * :2\r\n<br>
 * :3\r\n<br>
 * :4\r\n<br>
 * $6\r\n<br>
 * foobar\r\n<br>
 * (为了方便阅读，应答分成多行来展示)
 * <p>
 * 第一个行表示 *5\r\n 说明后面有5个应答。这些应答组成一个大的应答一起发送。
 * <p>
 * 空数组的概念也是存在的，另一个表示空值的方式(通常使用多行空字符串，历史遗留导致有这两种格式)。
 * <p>
 * 例如，当 BLPOP 命令超时，它会返回一个空数组，数组的计数器是-1 :
 * <p>
 * "*-1\r\n"
 *
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2021/12/31 17:59
 */
public record RespArrays(@Getter Resp[] array) implements Resp {

    public static RespArrays of(final List<Resp> array) {
        return new RespArrays(array.toArray(new Resp[0]));
    }

    public static String stringify(Resp[] array) {
        return CollUtil.join(CollUtil.map(ListUtil.toList(array), resp -> ((RespBulkStrings) resp).content().toString().toLowerCase(Locale.ROOT), true), " ");
    }

    public String stringify() {
        return stringify(array);
    }
}