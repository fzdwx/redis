package like.redis;

/**
 * redis server.
 *
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2021/12/31 17:31
 */
public interface RedisServer {

    /**
     * 启动redis server
     */
    void start();

    /**
     * 关闭redis server
     */
    void close();
}