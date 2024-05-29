package com.sltx.validator;

import com.jfinal.core.Controller;
import com.sltx.common.base.JsonValidator;

/**
 * systemResource Verifier
 * @author Rlax
 *
 */
public class ResValidator extends JsonValidator {

    @Override
    protected void validate(Controller c) {
        String methodName = getActionMethod().getName();
        if ("postAdd".equals(methodName)) {
            validateRequiredString("pid", "Parent resource code is empty");
            validateRequiredString("res.name", "Resource name is empty");
            validateRequiredString("res.url", "Resource URL is empty");
            validateRequiredString("res.des", "Resource description is empty");
            validateRequiredString("res.type", "Resource type is empty");
            validateRequiredString("res.seq", "Sorting number is empty");
            validateRequiredString("res.status", "Resource status is empty");
        } else if ("postUpdate".equals(methodName)) {
            validateRequiredString("pid", "Parent resource code is empty");
            validateRequiredString("res.name", "Resource name is empty");
            validateRequiredString("res.url", "Resource URL is empty");
            validateRequiredString("res.des", "Resource description is empty");
            validateRequiredString("res.type", "Resource type is empty");
            validateRequiredString("res.seq", "Sorting number is empty");
            validateRequiredString("res.status", "Resource status is empty");
        }
    }
}
