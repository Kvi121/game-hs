package org.game.hs;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
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


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        LOGGER.info("hello get msg:" + msg.getClass().getName());
    }
}
