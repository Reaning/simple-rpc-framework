package cn.lu.rpc.handler;

import cn.lu.rpc.entity.Message;
import cn.lu.rpc.entity.RpcMessageRequest;
import cn.lu.rpc.entity.RpcMessageResponse;
import cn.lu.rpc.service.ServiceRegistry;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

/**
 * cn.lu.cn.lu.rpc.handler
 *
 * @author lkxBruce
 * @date 2022/4/5 0:18
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
@Service
public class RpcRequestHandler extends SimpleChannelInboundHandler<RpcMessageRequest> implements SimpleRpcRequestHandler{

    @Autowired
    private ServiceRegistry serviceRegistry;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcMessageRequest rpcMessageRequest) throws Exception {
        String interfaceName = rpcMessageRequest.getInterfaceName();
        String methodName = rpcMessageRequest.getMethodName();
        // 调用函数
        RpcMessageResponse response = new RpcMessageResponse();
        response.setSequenceId(rpcMessageRequest.getSequenceId());
        response.setMessageType(Message.RPC_MESSAGE_RESPONSE);
        Object service = serviceRegistry.getService(interfaceName);
        Method method = service.getClass().getMethod(rpcMessageRequest.getMethodName(), rpcMessageRequest.getParameterTypes());
        method.setAccessible(true);
        Object result = method.invoke(service, rpcMessageRequest.getParameterValues());
        response.setResult(result);
        response.setException(null);
        channelHandlerContext.writeAndFlush(response);
    }
}
