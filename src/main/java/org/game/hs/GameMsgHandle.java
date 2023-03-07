package org.game.hs;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception{
        super.channelActive(ctx);
        channelGroup.add(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        LOGGER.info("hello get msg:" + msg.getClass().getName());
        //业务逻辑处理
        if(msg instanceof GameMsgProtocol.UserEntryCmd){
            GameMsgProtocol.UserEntryResult.Builder resultBuilder = GameMsgProtocol.
        }
    }
}
