package com.sltx.service.api;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.sltx.entity.model.Data;

import java.util.List;
import java.util.Map;

public interface DataService  {
    

    public Page<Data> findPage(Data data, int pageNumber, int pageSize);

    

    public String getCodeDescByCodeAndType(String code, String type);


    public String getCodeByCodeDescAndType(String type, String codeDesc);


    public Map<String, String> getMapByTypeOnUse(String type);


    public Map<String, String> getMapByType(String type);


    public List<Data> getListByTypeOnUse(String type);


    public List<Data> getListByType(String type);


    public void refreshCache();


    public Data findById(Object id);



    public boolean deleteById(Object id);


    public boolean delete(Data model);



    public boolean save(Data model);


    public boolean saveOrUpdate(Data model);


    public boolean update(Data model);


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