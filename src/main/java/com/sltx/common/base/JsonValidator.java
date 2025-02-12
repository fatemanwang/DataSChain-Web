package com.sltx.common.base;

import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.sltx.common.model.RestResult;
import com.sltx.common.model.RetResult;
import com.sltx.common.utils.RegexKey;

/**
 * Json validation base class
 * @author Rlax
 *
 */
public abstract class JsonValidator extends BaseValidator {

    public static final String ERROR_MSG_KEY = "_errorMsg";

    protected void addError(String message) {
        super.addError(ERROR_MSG_KEY, message);
    }

    @Override
    protected void handleError(Controller c) {
        RestResult<String> result = new RestResult<String>();
        result.error(c.getAttr(ERROR_MSG_KEY).toString());
        c.renderJson(result);
    }

    /**
     * check required
     * @param field
     * @param errorMessage
     */
    protected void validateRequiredString(String field, String errorMessage) {
        super.validateRequiredString(field, ERROR_MSG_KEY, errorMessage);
    }

    /**
     * check string
     * @param field
     * @param minLen
     * @param maxLen
     */
    protected void validateString(String field, int minLen, int maxLen, String message) {
        super.validateString(field, minLen, maxLen, ERROR_MSG_KEY, message);
    }

    /**
     * Verify image verification code
     * @param field
     * @param errorMessage
     */
    protected void validateCaptcha(String field, String errorMessage) {
        super.validateCaptcha(field, ERROR_MSG_KEY, errorMessage);
    }

    protected void validateInteger(String field, String errorMessage) {
        super.validateInteger(field, ERROR_MSG_KEY, errorMessage);
    }

    protected void validateInteger(String field, int min, int max, String errorMessage) {
        super.validateInteger(field, min, max, ERROR_MSG_KEY, errorMessage);
    }

    protected void validateLong(String field, String errorMessage) {
        super.validateLong(field, ERROR_MSG_KEY, errorMessage);
    }

    protected void validateLong(String field, long min, long max, String errorMessage) {
        super.validateLong(field, min, max, ERROR_MSG_KEY, errorMessage);
    }

    protected void validateDate(String field, String errorMessage) {
        super.validateDate(field, ERROR_MSG_KEY, errorMessage);
    }

    protected void validateDate(String field, String min, String max, String errorMessage) {
        super.validateDate(field, min, max, ERROR_MSG_KEY, errorMessage);
    }

    protected void validateEmail(String field, String errorMessage) {
        super.validateEmail(field, ERROR_MSG_KEY, errorMessage);
    }

    protected void validateMobile(String field, String errorMessage) {
        validateRegex(field, RegexKey.MOBILE, false, errorMessage);
    }

    protected void validateUrl(String field, String errorMessage) {
        super.validateUrl(field, ERROR_MSG_KEY, errorMessage);
    }

    protected void validateRegex(String field, String regExpression, boolean isCaseSensitive, String errorMessage) {
        super.validateRegex(field, regExpression, isCaseSensitive, ERROR_MSG_KEY, errorMessage);
    }

    protected void validateRegex(String field, String regExpression, String errorMessage) {
        super.validateRegex(field, regExpression, ERROR_MSG_KEY, errorMessage);
    }

    protected void validateToken(String errorMessage) {
        super.validateToken(ERROR_MSG_KEY, errorMessage);
    }

}
