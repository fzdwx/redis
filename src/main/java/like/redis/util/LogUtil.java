package like.redis.util;

import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2022/1/3 11:18
 */
public class LogUtil {

    private static final InternalLogger log = InternalLoggerFactory.getInstance(NioServerSocketChannel.class);

    private static final String MSG_PREFIX = "[ Redis server ] |- ";

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

    public static void info(String format, Object arg) {
        log.info(MSG_PREFIX + format, arg);
    }

    public static void info(String format, Object arg1, Object arg2) {
        log.info(MSG_PREFIX + format, arg1, arg2);
    }

    public static void info(String format, Object... arguments) {
        log.info(MSG_PREFIX, arguments);
    }

    public static void info(String msg, Throwable t) {
        log.info(MSG_PREFIX + msg, t);
    }

    public static void info(String msg) {
        log.info(MSG_PREFIX + msg);
    }

    // ===================================================

    public static void warn(String format, Object arg) {
        log.warn(MSG_PREFIX + format, arg);
    }

    public static void warn(String format, Object arg1, Object arg2) {
        log.warn(MSG_PREFIX + format, arg1, arg2);
    }

    public static void warn(String format, Object... arguments) {
        log.warn(MSG_PREFIX, arguments);
    }

    public static void warn(String msg, Throwable t) {
        log.warn(MSG_PREFIX + msg, t);
    }

    public static void warn(String msg) {
        log.warn(MSG_PREFIX + msg);
    }
}