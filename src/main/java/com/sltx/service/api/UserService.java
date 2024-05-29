package com.sltx.service.api;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.sltx.common.model.RetResult;
import com.sltx.entity.model.User;

import java.util.List;

public interface UserService  {

    public Page<User> findPage(User sysUser, int pageNumber, int pageSize);

    public boolean hasUser(String name);

    public User findByName(String name);

    public boolean saveUser(User user, Long[] roles);

    public boolean updateUser(User user, Long[] roles);

    public User findById(Object id);

    public boolean deleteById(Object id);

    public boolean delete(User model);


    public boolean save(User model);

    public boolean saveOrUpdate(User model);

    public boolean update(User model);


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

    public List<User> findByStatusUsed();
}