package org.game.hs.cmdHandler;

import com.google.protobuf.GeneratedMessageV3;
import protobuf.GameMsgProtocol;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Function:
 *
 * @author ZhangWY
 * @date: 2023/3/8
 * @since JDK 1.8
 */
public final class CmdHandlerFactory {
    private static Map<Class<?>,ICmdHandler<? extends GeneratedMessageV3>> handlerMap = new HashMap<>();


    private CmdHandlerFactory(){

    }

    public static void init(){
        handlerMap.put(GameMsgProtocol.UserEntryCmd.class, new UserEntryCmdHandler());
    }

    public static ICmdHandler<? extends GeneratedMessageV3> create(Class<?> msg){
        return handlerMap.get(msg);
    }
}
