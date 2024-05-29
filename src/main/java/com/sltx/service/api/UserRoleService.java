package com.sltx.service.api;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.sltx.entity.model.UserRole;

import java.util.List;

public interface UserRoleService  {

    public int deleteByUserId(Long userId);


    public int[] batchSave(List<UserRole> list);


    public UserRole findById(Object id);



    public boolean deleteById(Object id);


    public boolean delete(UserRole model);


    public boolean save(UserRole model);

    public boolean saveOrUpdate(UserRole model);

    public boolean update(UserRole model);


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