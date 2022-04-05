package cn.lu.rpc.protocol;

import io.netty.util.concurrent.Promise;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * cn.lu.rpc.protocol
 *
 * @author lkxBruce
 * @date 2022/4/5 0:54
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
public abstract class SequenceNum {
    public static final AtomicInteger SEQUENCE_CREATOR = new AtomicInteger(0);

    public static synchronized Integer getSequenceNum(){
        return SEQUENCE_CREATOR.getAndIncrement();
    }

    public static final Map<Integer, Promise<Object>> invokeResultMap = new HashMap<>();
}
