package com.sltx.entity.status;


import com.sltx.common.model.BaseStatus;

/**
 * System Resource Status Class
 * @author Rlax
 *
 */
public class DataStatus extends BaseStatus {

    public final static String UNUSED = "0";
    public final static String USED = "1";

    public DataStatus() {
        add(UNUSED, "unused");
        add(USED, "used");
    }

    private static DataStatus me;

    public static DataStatus me() {
        if (me == null) {
            me = new DataStatus();
        }
        return me;
    }

}
