package com.sltx.validator;

import com.jfinal.core.Controller;
import com.sltx.common.base.JsonValidator;

/**
 * login Verification
 * @author Rlax
 *
 */
public class LoginValidator extends JsonValidator {

    @Override
    protected void validate(Controller c) {
        System.out.println("welcome");
        validateString("loginName", 4, 16, "Username format is incorrect");
        validateString("password", 6, 16, "Password format is incorrect");
        validateString("capval", 4, 4, "Verification code format is incorrect");
        validateCaptcha("capval", "Incorrect verification code");
    }
}
