package com.sltx.service.api;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.sltx.entity.model.RoleRes;

import java.util.List;

public interface RoleResService  {

    public int deleteByRoleId(Long roleId);

    public int[] batchSave(List<RoleRes> list);

    public RoleRes findById(Object id);


    public boolean deleteById(Object id);

    public boolean delete(RoleRes model);


    public boolean save(RoleRes model);


    public boolean saveOrUpdate(RoleRes model);

    public boolean update(RoleRes model);


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