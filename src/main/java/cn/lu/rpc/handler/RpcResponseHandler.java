package cn.lu.rpc.handler;

import cn.lu.rpc.entity.RpcMessageResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

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

    }
}
