package cn.lu.rpc.entity;

import cn.lu.rpc.protocol.SequenceNum;
import lombok.Data;

/**
 * cn.lu.gpc.entity
 *
 * @author lkxBruce
 * @date 2022/4/4 23:17
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
@Data
public class RpcMessageRequest extends Message{
    /**
     * 接口全限定名
     */
    private String interfaceName;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 返回类型
     */
    private Class<?> returnType;
    /**
     * 参数类型数组
     */
    private Class<?>[] parameterTypes;
    /**
     * 参数值数组
     */
    private Object[] parameterValues;

    public RpcMessageRequest(int sequenceNum,String interfaceName, String methodName, Class<?> returnType, Class<?>[] parameterTypes, Object[] parameterValues) {
        super.setSequenceId(sequenceNum);
        super.setMessageType(getMessageType());
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.returnType = returnType;
        this.parameterTypes = parameterTypes;
        this.parameterValues = parameterValues;
    }

    public RpcMessageRequest(){
        super.setSequenceId(SequenceNum.getSequenceNum());

    }

    @Override
    public int getMessageType() {
        return RPC_MESSAGE_REQUEST;
    }
}
