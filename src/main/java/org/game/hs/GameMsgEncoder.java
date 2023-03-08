package org.game.hs;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protobuf.GameMsgProtocol;

import java.nio.ByteBuffer;

/**
 * Function: 消息编码器 - netty out
 *
 * @author ZhangWY
 * @date: 2023/3/7
 * @since JDK 1.8
 */
public class GameMsgEncoder extends ChannelOutboundHandlerAdapter {

    static private final Logger LOGGER = LoggerFactory.getLogger(GameMsgEncoder.class);


    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if( !(msg instanceof GeneratedMessageV3) || msg == null){
            super.write(ctx,msg, promise);
            return;
        }

        int msgCode = -1;

        if(msg instanceof  GameMsgProtocol.UserEntryResult){
            msgCode = GameMsgProtocol.MsgCode.USER_ENTRY_RESULT_VALUE;
            LOGGER.info("GameMsgEncoder : UserEntryResult");
        }
        if(msg instanceof  GameMsgProtocol.UserQuitResult){
            msgCode = GameMsgProtocol.MsgCode.USER_QUIT_RESULT_VALUE;
            LOGGER.info("GameMsgEncoder : UserQuitResult");
        }

        byte[] byteArray = ((GeneratedMessageV3) msg).toByteArray();
        ByteBuf byteBuffer = ctx.alloc().buffer();
        byteBuffer.writeShort((short)0);
        byteBuffer.writeShort((short)msgCode);
        byteBuffer.writeBytes(byteArray);

        BinaryWebSocketFrame frame = new BinaryWebSocketFrame(byteBuffer);
        super.write(ctx,frame,promise);
    }
}
