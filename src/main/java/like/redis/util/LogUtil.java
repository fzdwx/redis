package like.redis.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2022/1/3 11:18
 */
@Slf4j
public class LogUtil {

    private static final String MSG_PREFIX = "[Redis server ]|- ";

    public static void trace(String msg) {
        log.trace(MSG_PREFIX + msg);
    }

    public static void trace(String format, Object arg) {
        log.trace(MSG_PREFIX + format, arg);
    }

    public static void trace(String format, Object arg1, Object arg2) {
        log.trace(MSG_PREFIX + format, arg1, arg2);
    }

    public static void trace(String format, Object... arguments) {
        log.trace(MSG_PREFIX, arguments);
    }

    public static void trace(String msg, Throwable t) {
        log.trace(MSG_PREFIX + msg, t);
    }

    // ===================================================

    public static void debug(String format, Object arg) {
        log.debug(MSG_PREFIX + format, arg);
    }

    public static void debug(String format, Object arg1, Object arg2) {
        log.debug(MSG_PREFIX + format, arg1, arg2);
    }

    public static void debug(String format, Object... arguments) {
        log.debug(MSG_PREFIX, arguments);
    }

    public static void debug(String msg, Throwable t) {
        log.debug(MSG_PREFIX + msg, t);
    }

    public static void debug(String msg) {
        log.debug(MSG_PREFIX + msg);
    }
    
    // ===================================================

    public static void error(String format, Object arg) {
        log.error(MSG_PREFIX + format, arg);
    }

    public static void error(String format, Object arg1, Object arg2) {
        log.error(MSG_PREFIX + format, arg1, arg2);
    }

    public static void error(String format, Object... arguments) {
        log.error(MSG_PREFIX, arguments);
    }

    public static void error(String msg, Throwable t) {
        log.error(MSG_PREFIX + msg, t);
    }

    public static void error(String msg) {
        log.error(MSG_PREFIX + msg);
    }
    
    // ===================================================


}