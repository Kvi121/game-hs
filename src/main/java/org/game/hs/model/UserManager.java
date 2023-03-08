package org.game.hs.model;

import lombok.Data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Function:
 *
 * @author ZhangWY
 * @date: 2023/3/8
 * @since JDK 1.8
 */
public final class UserManager {
    private UserManager(){

    }
    static private final Map<Integer, User> userMap = new HashMap<>();

    public static void remove(Integer key){
        userMap.remove(key);
    }

    public static void put(Integer key,User value){
        userMap.put(key,value);
    }

    public static Collection<User> listUser(){
        return userMap.values();
    }
}
