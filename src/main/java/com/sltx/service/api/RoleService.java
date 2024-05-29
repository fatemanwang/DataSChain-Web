package com.sltx.service.api;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.sltx.entity.model.Role;

import java.util.List;

public interface RoleService  {

    public Page<Role> findPage(Role sysRole, int pageNumber, int pageSize);

    public List<Role> findByUserName(String name);

    public boolean auth(Long id, String resIds);

    public List<Role> findByStatusUsed();
    

    public Role findById(Object id);


    public boolean deleteById(Object id);


    public boolean delete(Role model);


    public boolean save(Role model);


    public boolean saveOrUpdate(Role model);


    public boolean update(Role model);


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