package com.sltx.entity.status;


import com.sltx.common.model.BaseStatus;

/**
 * System user online status class
 * @author Rlax
 *
 */
public class UserOnlineStatus extends BaseStatus {

    public final static String OFFLINE = "0";
    public final static String ONLINE = "1";

    public UserOnlineStatus() {
        add(OFFLINE, "offline");
        add(ONLINE, "online");
    }

    private static UserOnlineStatus me;

    public static UserOnlineStatus me() {
        if (me == null) {
            me = new UserOnlineStatus();
        }
        return me;
    }
}
