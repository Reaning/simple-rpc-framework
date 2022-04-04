package cn.lu.rpc.handler;

import cn.lu.rpc.entity.Message;
import cn.lu.rpc.entity.RpcMessageRequest;
import cn.lu.rpc.entity.RpcMessageResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * cn.lu.rpc.handler
 *
 * @author lkxBruce
 * @date 2022/4/5 0:18
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
public class RpcRequestHandler extends SimpleChannelInboundHandler<RpcMessageRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcMessageRequest rpcMessageRequest) throws Exception {
        String interfaceName = rpcMessageRequest.getInterfaceName();
        String methodName = rpcMessageRequest.getMethodName();
        // 调用函数
        RpcMessageResponse response = new RpcMessageResponse();
        response.setSequenceId(rpcMessageRequest.getSequenceId());
        response.setMessageType(Message.RPC_MESSAGE_RESPONSE);
        response.setResult(new Object[]{"123"});
        response.setException(null);
        channelHandlerContext.writeAndFlush(response);
    }
}
