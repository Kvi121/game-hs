package org.game.hs;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Objects;

/**
 * Function:
 *
 * @author ZhangWY
 * @date: 2023/3/8
 * @since JDK 1.8
 */
public final class Broadcaster {
    private Broadcaster(){

    }

    private static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static void addChannel(Channel channel){
        channelGroup.add(channel);
    }

    public static void removeChannel(Channel channel){
        channelGroup.remove(channel);
    }

    public static void broadcaster(Object msg){
        if(Objects.isNull(msg)){
            return;
        }
        channelGroup.writeAndFlush(msg);
    }
}
