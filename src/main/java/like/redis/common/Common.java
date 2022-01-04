package like.redis.common;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2022/1/3 10:36
 */
public interface Common {

    int DEFAULT_DB = 0;

    String OS_NAME = "os.name";
    String REDIS_VERSION = "0.1";

    Charset UTF8 = StandardCharsets.UTF_8;


    /*              attr               */

    /**
     * channel attr id
     */
    String ATTR_CONNECTION_ID = "id";
    /**
     * channel attr 连接名
     */
    String ATTR_CONNECTION_CONNECTION_NAME = "connectionName";
    /**
     * channel attr 使用的db
     */
    String ATTR_CONNECTION_DB_NO = "db";
}