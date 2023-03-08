package org.game.hs.cmdHandler;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.ChannelHandlerContext;

/**
 * Function:
 *
 * @author ZhangWY
 * @date: 2023/3/8
 * @since JDK 1.8
 */
public interface ICmdHandler <Cmd extends GeneratedMessageV3>{

    void handler(ChannelHandlerContext ctx, Cmd cmd);

}
