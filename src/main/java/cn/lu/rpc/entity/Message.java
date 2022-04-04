package cn.lu.rpc.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * cn.lu.gpc.entity
 *
 * @author lkxBruce
 * @date 2022/4/4 23:12
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
@Data
public abstract class Message implements Serializable {
    private int sequenceId;
    private int messageType;

    public static final int Ping = 0;

    public static final int RPC_MESSAGE_REQUEST = 101;
    public static final int RPC_MESSAGE_RESPONSE = 102;

    public static Map<Integer,Class<?extends Message>> messageTypeMap = new HashMap<>();

    public abstract int getMessageType();

    static{
        messageTypeMap.put(RPC_MESSAGE_REQUEST,RpcMessageRequest.class);
        messageTypeMap.put(RPC_MESSAGE_RESPONSE,RpcMessageResponse.class);
    }

}
