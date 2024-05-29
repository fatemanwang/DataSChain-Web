package com.sltx.validator;

import com.jfinal.core.Controller;
import com.sltx.common.base.JsonValidator;
import com.sltx.common.utils.AuthUtils;
import com.sltx.entity.model.User;


/**
 * changePassword Verifier
 * @author Rlax
 *
 */
public class ChangePwdValidator extends JsonValidator {

    @Override
    protected void validate(Controller c) {
        String pwd =  c.getPara("user.pwd");
        String newPwd =  c.getPara("newPwd");
        String rePwd =  c.getPara("rePwd");

        validateRequiredString("user.pwd", "The old password cannot be empty");
        validateRequiredString("newPwd", "New password cannot be empty");
        validateRequiredString("rePwd", "Confirm password can not be blank");

        if(!newPwd.equals(rePwd)){
            addError("The password you entered twice is inconsistent, please re-enter it！");
        }

        User user = AuthUtils.getLoginUser();

        if(!AuthUtils.checkPwd(pwd, user.getPwd(), user.getSalt2())){
            addError("The original password is incorrect！");
        }
    }
}
