package like.redis.datatype;

/**
 * redis data structure.
 *
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2022/1/4 14:33
 */
public interface RedisDataStructure {

    /**
     * 获取当前key的超时时间
     *
     * @return long
     */
    long timeout();

    /**
     * 设置超时时间
     *
     * @param timeout 超时(单位:秒)
     */
    void timeout(long timeout);
}