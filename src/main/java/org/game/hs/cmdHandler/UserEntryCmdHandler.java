package org.game.hs.cmdHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import org.game.hs.Broadcaster;
import org.game.hs.model.User;
import org.game.hs.model.UserManager;
import protobuf.GameMsgProtocol;
import org.springframework.stereotype.Service;
/**
 * Function:
 *
 * @author ZhangWY
 * @date: 2023/3/8
 * @since JDK 1.8
 */
@Service
public class UserEntryCmdHandler implements ICmdHandler<GameMsgProtocol.UserEntryCmd> {

    @Override
    public void handler(ChannelHandlerContext ctx, GameMsgProtocol.UserEntryCmd userEntryCmd){
        GameMsgProtocol.UserEntryResult.Builder resultBuilder = GameMsgProtocol.UserEntryResult.newBuilder();
        resultBuilder.setUserId(userEntryCmd.getUserId());
        resultBuilder.setHeroAvatar(userEntryCmd.getHeroAvatar());

        Integer userId = userEntryCmd.getUserId();
        ctx.channel().attr(AttributeKey.valueOf("userId")).set(userId);

        UserManager.put(userId,new User(userId,userEntryCmd.getHeroAvatar()));
        //群发
        GameMsgProtocol.UserEntryResult build = resultBuilder.build();
        Broadcaster.broadcaster(build);
    }


}
