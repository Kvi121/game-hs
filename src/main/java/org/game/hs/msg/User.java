package org.game.hs.msg;

import lombok.Data;

/**
 * Function:
 *
 * @author ZhangWY
 * @date: 2023/3/8
 * @since JDK 1.8
 */
@Data
public class User {
    public User(Integer userId,String heroAvatar){
        this.userId = userId;
        this.heroAvatar = heroAvatar;
    }
    private Integer userId;
    private String heroAvatar;
}
