package com.sltx.entity.status;


import com.sltx.common.model.BaseStatus;

/**
 * System Role Status Class
 * @author Rlax
 *
 */
public class RoleStatus extends BaseStatus {

    public final static String UNUSED = "0";
    public final static String USED = "1";

    public RoleStatus() {
        add(UNUSED, "unused");
        add(USED, "used");
    }

    private static RoleStatus me;

    public static RoleStatus me() {
        if (me == null) {
            me = new RoleStatus();
        }
        return me;
    }
}
