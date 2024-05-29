package com.sltx.controller.system;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.sltx.common.base.BaseController;
import com.sltx.common.exception.BusinessException;
import com.sltx.common.model.DataTable;
import com.sltx.common.model.MenuItem;
import com.sltx.common.model.RestResult;
import com.sltx.common.utils.AuthUtils;
import com.sltx.common.utils.NotNullPara;
import com.sltx.entity.model.Res;
import com.sltx.entity.status.ResStatus;
import com.sltx.service.api.ResService;
import com.sltx.validator.ResValidator;

import io.jboot.component.swagger.ParamType;
import io.jboot.core.rpc.annotation.JbootrpcService;
import io.jboot.web.controller.annotation.RequestMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.*;

import javax.inject.Inject;


/**
 * System Resource Controller
 * @author Rlax
 *
 */
@RequestMapping("/system/res")
@Api(description = "Resource Management Interface Documentation", basePath = "/system/res", tags = "res")
public class ResController extends BaseController {

    @Inject
    private ResService resService;

    /**
     * index
     */
    public void index() {
        render("main.html");
    }

    /**
     * add
     */
    @NotNullPara({"pid"})
    public void add() {
        Long pid = getParaToLong("pid");

        Res pRes = new Res();
        if (pid == null || pid == 0) {
            pRes.setId(0L);
            pRes.setName("root node");
        } else {
            pRes = resService.findById(pid);
        }

        setAttr("pRes", pRes).render("add.html");

    }


    @Before({POST.class, ResValidator.class})
    public void postAdd() {
        Long pid = getParaToLong("pid");
        Res sysRes = getBean(Res.class, "res");

        if (pid != 0) {
            Res pRes = resService.findById(pid);
            if (pRes == null) {
                throw new BusinessException("parent resource does not exist");
            } else {
                sysRes.setLevel(pRes.getLevel() + 1);
            }
        } else {
            sysRes.setLevel(1);
        }

        if (StrKit.isBlank(sysRes.getIconCls())) {
            sysRes.setIconCls("");
        }

        sysRes.setPid(pid);
        sysRes.setLastUpdAcct(AuthUtils.getLoginUser().getName());
        sysRes.setStatus(ResStatus.USED);
        sysRes.setLastUpdTime(new Date());
        sysRes.setNote("save system resources");

        if (!resService.save(sysRes)) {
            throw new BusinessException("Failed to save, please try again");
        }

        renderJson(RestResult.buildSuccess());
    }

    /**
     * update
     */
    @NotNullPara({"id"})
    public void update() {
        Long id = getParaToLong("id");

        Res sysRes = resService.findById(id);
        Res pRes = new Res();
        if (sysRes.getPid() == null || sysRes.getPid() == 0) {
            pRes.setId(0L);
            pRes.setName("root node");
        } else {
            pRes = resService.findById(sysRes.getPid());
        }

        setAttr("pRes", pRes).setAttr("res", sysRes).render("update.html");
    }


    @Before({POST.class, ResValidator.class})
    public void postUpdate() {
        Long pid = getParaToLong("pid");
        Res sysRes = getBean(Res.class, "res");

        if (StrKit.isBlank(sysRes.getIconCls())) {
            sysRes.setIconCls("");
        }

        sysRes.setPid(pid);
        sysRes.setLastUpdAcct(AuthUtils.getLoginUser().getName());
        sysRes.setLastUpdTime(new Date());
        sysRes.setNote("modify system resources");

        if (!resService.update(sysRes)) {
            throw new BusinessException("Modification failed, please try again");
        }

        renderJson(RestResult.buildSuccess());
    }


    @NotNullPara({"id"})
    public void delete() {
        Long id = getParaToLong("id");

        if (resService.hasChildRes(id)) {
            throw new BusinessException("System resources exist in lower-level resource nodes, which cannot be deleted");
        }

        if (!resService.deleteById(id)) {
            throw new BusinessException("failed to delete");
        }

        renderJson(RestResult.buildSuccess());
    }


    @NotNullPara({"id"})
    public void use() {
        Long id = getParaToLong("id");

        Res sysRes = resService.findById(id);
        if (sysRes == null) {
            throw new BusinessException("No" + id + "resource does not exist");
        }
        sysRes.setStatus(ResStatus.USED);
        sysRes.setLastUpdTime(new Date());
        sysRes.setNote("enable System Resources");

        if (!resService.update(sysRes)) {
            throw new BusinessException("enabling Failed");
        }

        renderJson(RestResult.buildSuccess());
    }

    /**
     * deactivateResources
     */
    @NotNullPara({"id"})
    public void unuse() {
        Long id = getParaToLong("id");

        Res sysRes = resService.findById(id);
        if (sysRes == null) {
            throw new BusinessException("No" + id + "resource does not exist");
        }

        sysRes.setStatus(ResStatus.UNUSED);
        sysRes.setLastUpdTime(new Date());
        sysRes.setNote("Deactivate System Resources");

        if (!resService.update(sysRes)) {
            throw new BusinessException("deactivation Failed");
        }

        renderJson(RestResult.buildSuccess());
    }


    public void resTree() {
        renderJson(RestResult.buildSuccess(resService.findTreeOnUse()));
    }


    @NotNullPara({"id"})
    public void resAuthTree() {
        renderJson(RestResult.buildSuccess(resService.findTreeOnUseByRoleId(getParaToLong("id"))));
    }


    @ApiOperation(value = "table data", httpMethod = "GET", notes = "resData")
    public void resData() {
        int pageNumber = getParaToInt("pageNumber", 1);
        int pageSize = getParaToInt("pageSize", 30);

        Res sysRes = new Res();
        sysRes.setPid(getParaToLong("pid", 0L));
        sysRes.setName(getPara("name"));
        sysRes.setUrl(getPara("url"));

        Page<Res> resPage = resService.findPage(sysRes, pageNumber, pageSize);
        renderJson(new DataTable<Res>(resPage));
    }


    @ApiOperation(value = "topMenu", httpMethod = "GET", notes = "menuTop")
    public void menuTop() {
        List<Res> list = resService.findTopMenuByUserName(AuthUtils.getLoginUser().getName());

        List<MenuItem> listL1 = null;
        for (Res l1 : list) {
            if (listL1 == null) {
                listL1 = new LinkedList<MenuItem>();
            }
            MenuItem l1Item = new MenuItem(l1.getName(), l1.getIconCls(), l1.getUrl(), l1.getId());
            listL1.add(l1Item);
        }

        Map<String, List<MenuItem>> menu = new HashMap<String, List<MenuItem>>();
        menu.put("data", listL1);

        renderJson(menu);
    }


    @NotNullPara({"pid"})
    public void menuLeft() {
        Long pid = getParaToLong("pid");
        List<Res> list = resService.findLeftMenuByUserNameAndPid(AuthUtils.getLoginUser().getName(), pid);

        List<MenuItem> listL1 = null;
        for (Res l1 : list) {

            if (l1.getLevel() == 2) {
                if (listL1 == null) {
                    listL1 = new LinkedList<MenuItem>();
                }
                MenuItem l1Item = new MenuItem(l1.getName(), l1.getIconCls(), l1.getUrl(), l1.getId());
                List<MenuItem> subset = null;

                for (Res l2 : list) {
                    if (l2.getLevel() == 3 && l2.getPid().equals(l1.getId())) {
                        if (subset == null) {
                            subset = new LinkedList<MenuItem>();
                        }
                        MenuItem l2Item = new MenuItem(l2.getName(), l2.getIconCls(), l2.getUrl(), l2.getId());
                        subset.add(l2Item);
                    }
                }
                l1Item.setSubset(subset);
                listL1.add(l1Item);
            }
        }

        Map<String, List<MenuItem>> menu = new HashMap<String, List<MenuItem>>();
        menu.put("data", listL1);
//        for (MenuItem date : menu.get("data")) {
//            List <MenuItem>aaa=date.getSubset();
//            System.out.println(date.getText());
//            for (MenuItem num : aaa) {
//                System.out.println(num.getText());
//            }
//        }
        renderJson(menu);
    }

}
