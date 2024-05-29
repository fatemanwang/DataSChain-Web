package com.sltx.entity.status;


import com.sltx.common.model.BaseStatus;

/**
 * System Resource Status Class
 * @author Rlax
 *
 */
public class ResStatus extends BaseStatus {

    public final static String UNUSED = "0";
    public final static String USED = "1";

    public ResStatus() {
        add(UNUSED, "unused");
        add(USED, "used");
    }

    private static ResStatus me;

    public static ResStatus me() {
        if (me == null) {
            me = new ResStatus();
        }
        return me;
    }

}
