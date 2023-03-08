package org.game.hs;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import org.game.hs.msg.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protobuf.GameMsgProtocol;

import java.util.HashMap;
import java.util.Map;
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

    static private Map<Integer, User> userMap = new HashMap<>();

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
        userMap.remove(userId);
        GameMsgProtocol.UserQuitResult.Builder resultBuilder = GameMsgProtocol.UserQuitResult.newBuilder();
        resultBuilder.setQuitUserId(userId);
        GameMsgProtocol.UserQuitResult quitResult = resultBuilder.build();
        Broadcaster.broadcaster(quitResult);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        LOGGER.info("hello get msg:" + msg.getClass().getName());
        //业务逻辑处理
        if(msg instanceof GameMsgProtocol.UserEntryCmd){

            GameMsgProtocol.UserEntryCmd userEntryCmd = (GameMsgProtocol.UserEntryCmd) msg;

            GameMsgProtocol.UserEntryResult.Builder resultBuilder = GameMsgProtocol.UserEntryResult.newBuilder();
            resultBuilder.setUserId(userEntryCmd.getUserId());
            resultBuilder.setHeroAvatar(userEntryCmd.getHeroAvatar());

            Integer userId = userEntryCmd.getUserId();
            ctx.channel().attr(AttributeKey.valueOf("userId")).set(userId);

            userMap.put(userId,new User(userId,userEntryCmd.getHeroAvatar()));
            //群发
            GameMsgProtocol.UserEntryResult build = resultBuilder.build();
            Broadcaster.broadcaster(build);
        }
    }
}
