package org.game.hs;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import org.game.hs.cmdHandler.CmdHandlerFactory;
import org.game.hs.cmdHandler.ICmdHandler;
import org.game.hs.cmdHandler.UserEntryCmdHandler;
import org.game.hs.model.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protobuf.GameMsgProtocol;

import java.util.Objects;

/**
 * Function:
 *
 * @author ZhangWY
 * @date: 2023/3/6
 * @since JDK 1.8
 */

public class GameMsgHandle extends SimpleChannelInboundHandler<Object> {
    /**
     * 日志对象
     */
    static private final Logger LOGGER = LoggerFactory.getLogger(GameMsgHandle.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception{
        super.channelActive(ctx);
        Broadcaster.addChannel(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception{
        super.handlerRemoved(ctx);
        Broadcaster.removeChannel(ctx.channel());

        Integer userId = (Integer) ctx.channel().attr(AttributeKey.valueOf("userId")).get();

        if(Objects.isNull(userId)){
            return;
        }
        UserManager.remove(userId);
        GameMsgProtocol.UserQuitResult.Builder resultBuilder = GameMsgProtocol.UserQuitResult.newBuilder();
        resultBuilder.setQuitUserId(userId);
        GameMsgProtocol.UserQuitResult quitResult = resultBuilder.build();
        Broadcaster.broadcaster(quitResult);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        LOGGER.info("hello get msg:" + msg.getClass().getName());

        ICmdHandler<? extends GeneratedMessageV3> cmdHandler = CmdHandlerFactory.create(msg.getClass());

        if(cmdHandler != null){
            cmdHandler.handler(ctx,cast(msg));
        }
    }

    private static <Cmd extends GeneratedMessageV3> Cmd cast(Object msg){
        if (null == msg || !(msg instanceof GeneratedMessageV3)) {
            return null;
        } else {
            return (Cmd) msg;
        }
    }
}
