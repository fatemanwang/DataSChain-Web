package com.sltx.service.api;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.sltx.common.model.ZTree;
import com.sltx.entity.model.Res;

import java.util.List;

public interface ResService  {


    public Page<Res> findPage(Res sysRes, int pageNumber, int pageSize);


    public List<ZTree> findTreeOnUse();


    public List<ZTree> findAllTree();


    public List<ZTree> findTreeOnUseByRoleId(Long id);


    public List<Res> findByRoleIdAndStatusUsed(Long id);


    public List<Res> findByStatus(String status);


    public List<Res> findByUserNameAndStatusUsed(String name);


    public List<Res> findTopMenuByUserName(String name);


    public List<Res> findLeftMenuByUserNameAndPid(String name, Long pid);


    public boolean hasChildRes(Long id);


    public Res findById(Object id);



    public boolean deleteById(Object id);


    public boolean delete(Res model);



    public boolean save(Res model);


    public boolean saveOrUpdate(Res model);


    public boolean update(Res model);


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