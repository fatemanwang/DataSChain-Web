package com.sltx.service.provider;

import com.google.common.collect.Maps;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.sltx.common.utils.StringToList;
import com.sltx.config.HyperLedgerFabricConfig;
import com.sltx.entity.DTO.EntityDTO;
import com.sltx.service.api.EntityService;
import org.apache.commons.codec.binary.StringUtils;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.sdk.Peer;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class EntityServiceImpl implements EntityService {

    final Gateway gateway= new HyperLedgerFabricConfig().gateway("Org1MSP");

    public EntityServiceImpl() throws Exception {

    }

    @Override
    public Page<EntityDTO> findPage(EntityDTO entity,int pageNum, int pageSize) throws ContractException {

        int total=0;
        int pages=0;
        Contract contract = getContract();
        List<EntityDTO> datalist=new ArrayList<>();

        if(StrKit.notBlank(entity.getUploader())){

            byte[] allEntities=contract.evaluateTransaction("queryEntityByUploader", entity.getUploader());
            datalist=StringToList.ByteToClassList(allEntities);

        }else{
            byte[] allEntities = contract.evaluateTransaction("queryAllEntities_2");
            datalist= StringToList.ByteToClassList(allEntities);
        }

        if(datalist.size()!=0){
            total=datalist.size();
            pages=(int)(total / (long)pageSize + (long)(total % (long)pageSize == 0L ? 0 : 1));
        }
        Page<EntityDTO> entityDTOPage =new Page<EntityDTO>(datalist,pageNum,pageSize,pages,datalist.size());
        return entityDTOPage;
    }

    @Override
    public EntityDTO queryEntityByKey(String id) throws ContractException {

            Map<String , Object> result = Maps.newConcurrentMap();
            Contract contract = getContract();
            byte[] entity = contract.evaluateTransaction("queryEntity", id);
            EntityDTO entityDTO=StringToList.ByteToClass(entity);

            result.put("payload" , StringUtils.newStringUtf8(entity));
            result.put("status" , "ok");

            return entityDTO;
    }

    @Override
    public Map<String, Object> createEntity(String key, String content, String uploader) throws ContractException, TimeoutException, InterruptedException {
        Map<String , Object> result = Maps.newConcurrentMap();
        Contract contract = getContract();

        byte[] bytes = contract.submitTransaction("createEntity", key,content,uploader);

        result.put("payload" , StringUtils.newStringUtf8(bytes));
        result.put("status" , "ok");

        return result;
    }

    @Override
    public Map<String, Object> postUpdate(String key, String content, String uploader) throws ContractException, TimeoutException, InterruptedException {
        Map<String , Object> result = Maps.newConcurrentMap();
        Contract contract = getContract();

        byte[] bytes = contract.submitTransaction("updateEntity", key, content,uploader);

        result.put("payload" , StringUtils.newStringUtf8(bytes));
        result.put("status" , "ok");

        return result;
    }

    @Override
    public Map<String, Object> deleteEntityByKey(String key) throws ContractException, TimeoutException, InterruptedException {
        Map<String , Object> result = Maps.newConcurrentMap();
        Contract contract = getContract();

        byte[] entity = contract.createTransaction("deleteEntity")
                .setEndorsingPeers(gateway.getNetwork("mychannel").getChannel().getPeers(EnumSet.of(Peer.PeerRole.ENDORSING_PEER)))
                .submit(key);

        result.put("payload" , StringUtils.newStringUtf8(entity));
        result.put("status" , "ok");

        return result;
    }


    private Contract getContract() {

        Network network = gateway.getNetwork("mychannel");

        return network.getContract("hyperledger-fabric-contract-java-datashare");
    }
}
