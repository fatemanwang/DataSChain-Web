package com.sltx.common.utils;

/**
 * Regular check constant class
 * @author Rlax
 *
 */
public class RegexKey {

    public final static String MOBILE = "^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\\d{8})$";

    public final static String EMAIL = "\\w+@(\\w+.)+[a-z]{2,3}";
}
