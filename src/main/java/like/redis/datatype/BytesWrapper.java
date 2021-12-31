package like.redis.datatype;

import like.redis.util.CharsetKit;
import lombok.Getter;

import java.util.Arrays;

/**
 * 字节包装类型
 *
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2021/12/31 17:52
 */
public class BytesWrapper implements Comparable<BytesWrapper> {

    @Getter
    private final byte[] content;

    public BytesWrapper(byte[] content) {
        this.content = content;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final BytesWrapper that = (BytesWrapper) o;
        return Arrays.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(content);
    }

    @Override
    public int compareTo(BytesWrapper o) {
        int len1 = content.length;
        int len2 = o.getContent().length;

        int min = Math.min(len1, len2);

        byte v2[] = o.getContent();

        int k = 0;
        while (k < min) {
            byte c1 = content[k];
            byte c2 = v2[k];
            if (c1 != c2) {
                return c1 - c2;
            }
            k++;
        }
        return len1 - len2;
    }

    @Override
    public String toString() {
        return new String(content, CharsetKit.UTF8);
    }
}