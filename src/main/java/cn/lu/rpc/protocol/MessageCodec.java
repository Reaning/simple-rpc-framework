package cn.lu.rpc.protocol;

import cn.lu.rpc.entity.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

/**
 * cn.lu.gpc.protocol
 *
 * @author lkxBruce
 * @date 2022/4/4 15:14
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
public class MessageCodec extends MessageToMessageCodec<ByteBuf, Message> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, List<Object> list) throws Exception {
        ByteBuf buffer = channelHandlerContext.alloc().buffer();
        // 魔数 4
        buffer.writeBytes(new byte[]{2,4,2,4});
        // 版本 1
        buffer.writeByte(1);
        // 序列化类型 1
        buffer.writeByte(Serializer.JSON_TYPE);
        // 消息类型 1
        buffer.writeByte(message.getMessageType());
        // 消息序列号 4
        buffer.writeInt(message.getSequenceId());
        // 填充 1
        buffer.writeByte(1);
        byte[] bytes = Serializer.Algorithm.Json.serialize(message);
        // 长度
        buffer.writeInt(bytes.length);
        // 数据
        buffer.writeBytes(bytes);
        System.out.println(message.getClass());
        list.add(buffer);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int magicNum = byteBuf.readInt();
        byte version = byteBuf.readByte();
        int serializeType = byteBuf.readByte();
        int messageType = byteBuf.readByte();
        int sequenceNum = byteBuf.readInt();
        byteBuf.readByte();
        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes,0,length);
        Class<?> clazz = Message.messageTypeMap.get(messageType);
        Message message = (Message) Serializer.Algorithm.Json.deserialize(clazz, bytes);
        list.add(message);
    }
}
