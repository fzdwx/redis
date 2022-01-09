package like.redis.client;

import cn.hutool.core.util.StrUtil;
import io.netty.channel.Channel;
import like.redis.protocal.Resp;
import like.redis.protocal.RespArrays;
import like.redis.protocal.RespBulkStrings;
import like.redis.protocal.RespErrors;
import like.redis.protocal.RespIntegers;
import like.redis.protocal.RespSimpleStrings;

import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 命令发送器
 * @author <a href="mailto:likelovec@gmail.com">like</a>
 * @date 2022/1/9 14:10
 */
public record CommandSender(Channel serverChannel) implements Runnable {

    private static final String PREFIX = "> ";

    private final static Scanner SCANNER = new Scanner(System.in);

    private static long start = 0;

    public static CommandSender create(Channel serverChannel) {
        return new CommandSender(serverChannel);
    }

    public static Consumer<Resp> respConsumer() {
        return resp -> {
            final var calculate = calculate();
            if (resp instanceof RespSimpleStrings s)
                println(s.content());
            else if (resp instanceof RespBulkStrings bs)
                println(bs.content());
            else if (resp instanceof RespArrays arrays)
                Arrays.stream(arrays.array()).forEach(respConsumer());
            else if (resp instanceof RespErrors errors)
                println("(error) " + errors.content());
            else if (resp instanceof RespIntegers integers)
                println("(int) " + integers.value());
            println("(cost " + calculate + " ms) ");
            print(PREFIX);
        };
    }

    @Override
    public void run() {
        print(PREFIX);
        while (true) {
            final var str = SCANNER.nextLine();
            serverChannel.writeAndFlush(RespArrays.of(StrUtil.split(str, " ").stream().map(RespBulkStrings::of).collect(Collectors.toList())));
            markTime();
        }
    }

    private static long calculate() {
        return System.currentTimeMillis() - start;
    }

    private static void markTime() {
        start = System.currentTimeMillis();
    }

    private static void print(String str) {
        System.out.print(str);
    }

    private static void println(Object str) {
        System.out.println(str);
    }
}
