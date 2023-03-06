package org.game.hs;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import protobuf.GameMsgProtocol;

/**
 * Function:
 *
 * @author Zhang
 * @date: 2023/3/6
 * @since JDK 1.8
 */
public class GameMsgDecoder extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(!(msg instanceof BinaryWebSocketFrame)){
            return;
        }

        BinaryWebSocketFrame frame = (BinaryWebSocketFrame) msg;
        ByteBuf content = frame.content();

        //读取消息长度
        content.readShort();
        //读取消息编号
        int msgCode = content.readShort();

        //拿到消息体
        byte[] msgBody = new byte[content.readableBytes()];
        content.readBytes(msgBody);

        GeneratedMessageV3 cmd = null;
        switch(msgCode){
            case GameMsgProtocol.MsgCode.USER_ENTRY_CMD_VALUE:
                cmd = GameMsgProtocol.UserEntryCmd.parseFrom(msgBody);
                break;
        }
        if(null != cmd){
            ctx.fireChannelRead(cmd);
        }

    }
}
