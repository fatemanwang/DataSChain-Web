package com.sltx.service.api;

import com.jfinal.plugin.activerecord.Page;
import com.sltx.entity.DTO.EntityDTO;
import org.hyperledger.fabric.gateway.ContractException;

import java.util.Map;
import java.util.concurrent.TimeoutException;

public interface EntityService {

    public Page<EntityDTO> findPage(EntityDTO entity,int pageNum,int pageSize) throws ContractException;

    public EntityDTO queryEntityByKey(String id) throws  ContractException;

    public Map<String ,Object> createEntity(String key,String content,String uploader) throws ContractException, TimeoutException, InterruptedException;

    public Map<String ,Object> postUpdate(String key,String content,String uploader) throws ContractException, TimeoutException, InterruptedException;

    public Map<String ,Object> deleteEntityByKey(String key) throws ContractException, TimeoutException, InterruptedException;
}
