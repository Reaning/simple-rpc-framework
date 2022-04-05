package cn.lu.rpc.handler;

import cn.lu.rpc.entity.RpcMessageResponse;
import cn.lu.rpc.protocol.SequenceNum;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Promise;

/**
 * cn.lu.rpc.handler
 *
 * @author lkxBruce
 * @date 2022/4/5 1:43
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
public class RpcResponseHandler extends SimpleChannelInboundHandler<RpcMessageResponse> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcMessageResponse rpcMessageResponse) throws Exception {
        Object result = rpcMessageResponse.getResult();
        Promise<Object> promise = SequenceNum.invokeResultMap.get(rpcMessageResponse.getSequenceId());
        promise.setSuccess(result);
    }
}
