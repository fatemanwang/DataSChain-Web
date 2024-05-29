package com.sltx.entity.status;

import com.sltx.common.model.BaseStatus;

/**
 * System User Status Class
 * @author Rlax
 *
 */
public class UserStatus extends BaseStatus {

    public final static String REGISTER = "0";
    public final static String USED = "1";
    public final static String LOCKED = "2";

    public UserStatus() {
        add(REGISTER, "REGISTER");
        add(USED, "USED");
        add(LOCKED, "LOCKED");
    }

    private static UserStatus me;

    public static UserStatus me() {
        if (me == null) {
            me = new UserStatus();
        }
        return me;
    }
}
