package cn.lu.rpc.entity;

import lombok.Data;

/**
 * cn.lu.gpc.entity
 *
 * @author lkxBruce
 * @date 2022/4/4 23:18
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
@Data
public class RpcMessageResponse extends Message{

    private Object result;
    private Exception exception;

    @Override
    public int getMessageType() {
        return RPC_MESSAGE_RESPONSE;
    }
}
