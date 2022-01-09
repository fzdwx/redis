# Redis Java

Use _Netty 4.1.66.Final_ with _JDK 17_**(learning)**

## Redis RESP Protocol

Please see

1. [resp interface](/src/main/java/like/redis/protocal/Resp.java) is resp top level interface.
2. [resp simple string](/src/main/java/like/redis/protocal/RespSimpleStrings.java) is only line string type.
3. [resp bulk string](/src/main/java/like/redis/protocal/RespBulkStrings.java) is multi line string type.
4. [resp array](/src/main/java/like/redis/protocal/RespArrays.java) is array of resp.
5. [resp integer](/src/main/java/like/redis/protocal/RespIntegers.java) is integer of resp
6. [resp error](/src/main/java/like/redis/protocal/RespErrors.java) is error of resp

All of the above can be learned from the [RESP Protocol](https://www.redis.com.cn/topics/protocol.html)

## Redis Server Impl

1. See [default redis server impl](/src/main/java/like/redis/LikeRedisServer.java).
2. [redis core](/src/main/java/like/redis/server/RedisCoreImpl.java) is redis java core,that stores all kv data,and
   switch db.

## Simple Redis Client Impl

[redis client](/src/main/java/like/redis/client/RedisClient.java) can connect redis server,and send command
use `RespBulkStrings` dateType,client will submit a task
=> [command sender](/src/main/java/like/redis/client/CommandSender.java),they will send command and print response
information returned by the server.

## Command Impl

1 Now there are only some simple implementations.

1. [set](/src/main/java/like/redis/command/impl/string/Set.java)
2. [get](/src/main/java/like/redis/command/impl/string/Set.java)
3. ...
