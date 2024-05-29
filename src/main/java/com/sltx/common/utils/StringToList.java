package com.sltx.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sltx.entity.DTO.EntityDTO;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringToList {

    //Get the value of the attribute "entityList", the input value is "entityList", and the query result is EntityDTO
    public static List<EntityDTO> ByteToClassList(byte[] bytes){
        JSONObject jsonObject = JSON.parseObject(new String(bytes, StandardCharsets.UTF_8));
        String str = jsonObject.getString("entityList");  //Get the value of the value of the key "entityList"
        JSONArray jsonArray = JSONArray.parseArray(str);
        List<EntityDTO> entityDTOList=new ArrayList();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            EntityDTO enity=JSONObject.parseObject(object.toJSONString(),EntityDTO.class);
            entityDTOList.add(enity);
        }
        return entityDTOList;
    }

    //Get the value of the attribute "entities", the input value is "entities", and the query result is EntityDTO
    public static List<EntityDTO> ByteToClassList_2(byte[] bytes){
        JSONObject jsonObject=JSON.parseObject(new String(bytes, StandardCharsets.UTF_8));
        String listStr=jsonObject.getString("entities");
        JSONArray jsonArray = JSONArray.parseArray(listStr);
        List<EntityDTO> entityDTOList=new ArrayList();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            String  entityStr=object.getString("entity");
            JSONObject entityObj= JSON.parseObject(entityStr);
            EntityDTO entity=JSONObject.parseObject(entityObj.toJSONString(),EntityDTO.class);
            entityDTOList.add(entity);
        }
        return entityDTOList;
    }

    /*
    * Convert the byte Byte[] into the corresponding class,
    * input as Entity (in smart contract), and output EntityDTO
    * */
    public static EntityDTO ByteToClass(byte[] bytes){
        EntityDTO entityDTO=new EntityDTO();
        JSONObject jsonObject = JSON.parseObject(new String(bytes,StandardCharsets.UTF_8));
        entityDTO.setContent(jsonObject.getString("content"));
        entityDTO.setId(jsonObject.getString("id"));
        entityDTO.setUploader(jsonObject.getString("uploader"));
        entityDTO.setUptime(jsonObject.getString("uptime"));
        entityDTO.setUploadPermission(new ArrayList(Arrays.asList(jsonObject.getString("uploadPermission").split(","))));
        entityDTO.setCooperator(new ArrayList(Arrays.asList(jsonObject.getString("cooperator").split(","))));
        entityDTO.setDownloadPermission(new ArrayList(Arrays.asList(jsonObject.getString("downloadPermission").split(","))));
        entityDTO.setCID(jsonObject.getString("CID"));
        return entityDTO;
    }

    /*
    * Convert the byte Byte[] into the corresponding class,
    * the input is (in the smart contract) PrivateEntity, and the output is EntityDTO
    * */
    public static EntityDTO ByteToClass_private(byte[] bytes){
        JSONObject jsonObject = JSON.parseObject(new String(bytes,StandardCharsets.UTF_8));
        String entityStr=jsonObject.getString("entity");
        JSONObject entity= JSON.parseObject(entityStr);
        EntityDTO entityDTO=JSONObject.parseObject(entity.toJSONString(),EntityDTO.class);
        return entityDTO;
    }

    //The input value is "privateEntityList", and the query result is EntityDTO
    public static List<EntityDTO> ByteToClassList_private(byte[] bytes){
        JSONObject jsonObject = JSON.parseObject(new String(bytes, StandardCharsets.UTF_8));
        String str = jsonObject.getString("privateEntityList");
        JSONArray jsonArray = JSONArray.parseArray(str);
        List<EntityDTO> entityDTOList=new ArrayList();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            String entityStr=object.getString("entity");
            JSONObject entity= JSON.parseObject(entityStr);
            EntityDTO entityDTO=JSONObject.parseObject(entity.toJSONString(),EntityDTO.class);
            entityDTOList.add(entityDTO);
        }
        return entityDTOList;
    }

}
