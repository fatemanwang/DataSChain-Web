package com.sltx.controller.system;

import com.jfinal.plugin.activerecord.Page;
import com.sltx.common.base.BaseController;
import com.sltx.common.exception.BusinessException;
import com.sltx.common.model.DataTable;
import com.sltx.common.model.RestResult;
import com.sltx.common.utils.AuthUtils;
import com.sltx.common.utils.NotNullPara;
import com.sltx.entity.model.Role;
import com.sltx.entity.status.RoleStatus;
import com.sltx.service.api.RoleService;
import com.sltx.shiro.ShiroCacheUtils;

import io.jboot.web.controller.annotation.RequestMapping;

import java.util.Date;

import javax.inject.Inject;

/**
 * System role management
 * @author Rlax
 *
 */
@RequestMapping("/system/role")
public class RoleController extends BaseController {

	//@JbootrpcService
	@Inject
    private RoleService roleService;


    /**
     * index
     */
    public void index() {
        render("main.html");
    }

    /**
     * res tabel data
     */
    public void tableData() {
        int pageNumber = getParaToInt("pageNumber", 1);
        int pageSize = getParaToInt("pageSize", 30);

        Role sysRole = new Role();
        sysRole.setName(getPara("name"));

        Page<Role> rolePage = roleService.findPage(sysRole, pageNumber, pageSize);
//        System.out.println(rolePage.getList().get(0).getClass());
        renderJson(new DataTable<Role>(rolePage));
    }

    /**
     * add
     */
    public void add() {
        render("add.html");
    }


    public void postAdd() {
        Role sysRole = getBean(Role.class, "role");

        sysRole.setLastUpdAcct(AuthUtils.getLoginUser().getName());
        sysRole.setStatus(RoleStatus.USED);
        sysRole.setLastUpdTime(new Date());
        sysRole.setNote("save system roles");

        if (!roleService.save(sysRole)) {
            throw new BusinessException("save failed");
        }

        renderJson(RestResult.buildSuccess());
    }

    /**
     * update
     */
    @NotNullPara({"id"})
    public void update() {
        Long id = getParaToLong("id");

        Role sysRole = roleService.findById(id);
        setAttr("role", sysRole).render("update.html");
    }


    public void postUpdate() {
        Role sysRole = getBean(Role.class, "role");

        sysRole.setLastUpdAcct(AuthUtils.getLoginUser().getName());
        sysRole.setLastUpdTime(new Date());
        sysRole.setNote("modify system resources");

        if (!roleService.update(sysRole)) {
            throw new BusinessException("modify failed");
        }

        renderJson(RestResult.buildSuccess());
    }

    @NotNullPara({"id"})
    public void delete() {
        Long id = getParaToLong("id");
        if (!roleService.deleteById(id)) {
            throw new BusinessException("delete failed");
        }

        renderJson(RestResult.buildSuccess());
    }


    @NotNullPara({"id"})
    public void auth() {
        Role sysRole = roleService.findById(getParaToLong("id"));
        setAttr("role", sysRole).render("auth.html");
    }


    @NotNullPara({"id","resIds"})
    public void postAuth() {
        String resIds = getPara("resIds");
        Long id = getParaToLong("id");

        if (!roleService.auth(id, resIds)) {
            throw new BusinessException("Empowerment failure");
        }

        ShiroCacheUtils.clearAuthorizationInfoAll();
        renderJson(RestResult.buildSuccess());
    }

}
