package cn.lu.rpc.protocol;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * cn.lu.gpc.protocol
 *
 * @author lkxBruce
 * @date 2022/4/4 16:26
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
public class FrameDecoder extends LengthFieldBasedFrameDecoder {
    public FrameDecoder() {
        this(1024,12,4,0,0);
    }
    public FrameDecoder(int maxFrameLength,int lengthFieldOffset,int lengthFieldLength,int lengthAdjustment,int initialBytesToStrip){
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }
}
