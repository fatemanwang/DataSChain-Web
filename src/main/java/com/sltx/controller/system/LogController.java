package com.sltx.controller.system;

import javax.inject.Inject;

import com.jfinal.plugin.activerecord.Page;
import com.sltx.common.base.BaseController;
import com.sltx.common.model.DataTable;
import com.sltx.entity.model.Log;
import com.sltx.service.api.LogService;

import io.jboot.core.rpc.annotation.JbootrpcService;
import io.jboot.web.controller.annotation.RequestMapping;

/**
 * log management
 * @author Rlax
 *
 */
@RequestMapping("/system/log")
public class LogController extends BaseController {
    
	//@JbootrpcService
	@Inject
    private LogService logService;

    /**
     * index
     */
    public void index() {
        System.out.println("hello...");
        render("main.html");
    }

    /**
     * tabel data
     */
    public void tableData() {
        int pageNumber = getParaToInt("pageNumber", 1);
        int pageSize = getParaToInt("pageSize", 30);

        Log log = new Log();
        log.setIp(getPara("ip"));
        log.setUrl(getPara("url"));
        log.setLastUpdAcct(getPara("userName"));

        Page<Log> logPage = logService.findPage(log, pageNumber, pageSize);
        renderJson(new DataTable<Log>(logPage));
    }
}
