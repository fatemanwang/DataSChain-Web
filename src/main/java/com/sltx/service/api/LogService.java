package com.sltx.service.api;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.sltx.entity.model.Log;

import java.util.List;

public interface LogService  {


    public Page<Log> findPage(Log log, int pageNumber, int pageSize);


    public Log findById(Object id);



    public boolean deleteById(Object id);


    public boolean delete(Log model);



    public boolean save(Log model);


    public boolean saveOrUpdate(Log model);


    public boolean update(Log model);


    public void join(Page<? extends Model> page, String joinOnField);
    public void join(Page<? extends Model> page, String joinOnField, String[] attrs);
    public void join(Page<? extends Model> page, String joinOnField, String joinName);
    public void join(Page<? extends Model> page, String joinOnField, String joinName, String[] attrs);


    public void join(List<? extends Model> models, String joinOnField);
    public void join(List<? extends Model> models, String joinOnField, String[] attrs);
    public void join(List<? extends Model> models, String joinOnField, String joinName);
    public void join(List<? extends Model> models, String joinOnField, String joinName, String[] attrs);


    public void join(Model model, String joinOnField);


    public void join(Model model, String joinOnField, String[] attrs);



    public void join(Model model, String joinOnField, String joinName);



    public void join(Model model, String joinOnField, String joinName, String[] attrs);


    public void keep(Model model, String... attrs);

    public void keep(List<? extends Model> models, String... attrs);
}