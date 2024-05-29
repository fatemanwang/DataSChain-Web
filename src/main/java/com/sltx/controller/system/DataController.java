package com.sltx.controller.system;

import com.jfinal.plugin.activerecord.Page;
import com.sltx.common.base.BaseController;
import com.sltx.common.exception.BusinessException;
import com.sltx.common.model.DataTable;
import com.sltx.common.model.RestResult;
import com.sltx.common.utils.AuthUtils;
import com.sltx.common.utils.NotNullPara;
import com.sltx.entity.model.Data;
import com.sltx.entity.status.DataStatus;
import com.sltx.service.api.DataService;

import io.jboot.core.rpc.annotation.JbootrpcService;
import io.jboot.web.controller.annotation.RequestMapping;

import java.util.Date;

import javax.inject.Inject;

/**
 * Data Dictionary Management
 * @author Rlax
 *
 */
@RequestMapping("/system/data")
public class DataController extends BaseController {

    //@JbootrpcService
	@Inject
    private DataService dataService;

    /**
     * index
     */
    public void index() {
        render("main.html");
    }

    /**
     * table data
     */
    public void tableData() {
        int pageNumber = getParaToInt("pageNumber", 1);
        int pageSize = getParaToInt("pageSize", 30);

        Data data = new Data();
        data.setType(getPara("type"));
        data.setTypeDesc(getPara("typeDesc"));

        Page<Data> dataPage = dataService.findPage(data, pageNumber, pageSize);
        renderJson(new DataTable<Data>(dataPage));
    }

    /**
     * add
     */
    public void add() {
        render("add.html");
    }


    public void postAdd() {
        Data data = getBean(Data.class, "data");

        data.setLastUpdAcct(AuthUtils.getLoginUser().getName());
        data.setStatus(DataStatus.USED);
        data.setCreateDate(new Date());
        data.setLastUpdTime(new Date());
        data.setNote("save data dictionary");

        if (!dataService.save(data)) {
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
        Data data = dataService.findById(id);

        setAttr("data", data).render("update.html");
    }

    /**
     * modify commit
     */
    public void postUpdate() {
        Data data = getBean(Data.class, "data");

        if (dataService.findById(data.getId()) == null) {
            throw new BusinessException("data does not exist");
        }

        data.setLastUpdAcct(AuthUtils.getLoginUser().getName());
        data.setStatus(DataStatus.USED);
        data.setLastUpdTime(new Date());
        data.setNote("modify data dictionary");

        if (!dataService.update(data)) {
            throw new BusinessException("fail to edit");
        }

        renderJson(RestResult.buildSuccess());
    }

    /**
     * delete
     */
    @NotNullPara({"id"})
    public void delete() {
        Long id = getParaToLong("id");
        if (!dataService.deleteById(id)) {
            throw new BusinessException("failed to delete");
        }

        renderJson(RestResult.buildSuccess());
    }

    /**
     * enable resource
     */
    @NotNullPara({"id"})
    public void use() {
        Long id = getParaToLong("id");

        Data data = dataService.findById(id);
        if (data == null) {
            throw new BusinessException("No" + id + "data does not exist");
        }
        data.setStatus(DataStatus.USED);
        data.setLastUpdTime(new Date());
        data.setNote("enable data");

        if (!dataService.update(data)) {
            throw new BusinessException("Failed to enable");
        }

        renderJson(RestResult.buildSuccess());
    }

    /**
     * Disable resources
     */
    @NotNullPara({"id"})
    public void unuse() {
        Long id = getParaToLong("id");
        Data data = dataService.findById(id);
        if (data == null) {
            throw new BusinessException("No" + id + "data does not exist");
        }

        data.setStatus(DataStatus.UNUSED);
        data.setLastUpdTime(new Date());
        data.setNote("disable data");
        data.update();

        if (!dataService.update(data)) {
            throw new BusinessException("Deactivation failed");
        }

        renderJson(RestResult.buildSuccess());
    }

    public void cache() {
        dataService.refreshCache();
        renderJson(RestResult.buildSuccess());
    }

}
