package com.sltx.service.provider;

import com.jfinal.plugin.activerecord.Db;

import com.sltx.entity.model.User;
import io.jboot.aop.annotation.Bean;

import com.sltx.entity.model.UserRole;
import com.sltx.service.api.UserRoleService;

import io.jboot.core.rpc.annotation.JbootrpcService;
import io.jboot.service.JbootServiceBase;

import javax.inject.Singleton;

import java.util.List;

@Bean
@Singleton
//@JbootrpcService
public class UserRoleServiceImpl extends JbootServiceBase<UserRole> implements UserRoleService {

    @Override
    public int deleteByUserId(Long userId) {
        return Db.update("delete from sys_user_role where user_id = ?", userId);
    }

    @Override
    public int[] batchSave(List<UserRole> list) {
        return Db.batchSave(list, list.size());
    }

    @Override
    public UserRole findById(Object id) {
        return DAO.findFirstByColumn("user_id", id);
    }
}