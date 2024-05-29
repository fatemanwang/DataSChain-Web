package com.sltx.controller.system;

import com.google.common.collect.Maps;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.sltx.common.base.BaseController;
import com.sltx.common.model.DataTable;
import com.sltx.common.model.RestResult;
import com.sltx.common.utils.AuthUtils;
import com.sltx.common.utils.StringToList;
import com.sltx.config.HyperLedgerFabricConfig;
import com.sltx.entity.DTO.EntityDTO;
import com.sltx.entity.DTO.PrivateEntityDTO;
import com.sltx.entity.model.User;
import com.sltx.service.api.RoleService;
import com.sltx.service.api.UserService;
import com.sltx.util.IPFSUtil;
import io.ipfs.api.IPFS;
import io.jboot.web.controller.annotation.RequestMapping;
import org.apache.commons.codec.binary.StringUtils;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.sdk.Peer;


import javax.inject.Inject;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeoutException;

@RequestMapping("/system/privateEntity")
public class PrivateEntityContractController extends BaseController {


//    @Inject
//    private EntityService entityService;

    final String mspId= AuthUtils.getLoginUser().getMspId();

    final Gateway gateway= new HyperLedgerFabricConfig().gateway(mspId);


    @Inject
    private RoleService roleService;

    @Inject
    private UserService userService;

    public PrivateEntityContractController() throws Exception {
    }

    /**
     * index
     */
    public void index(){
        render("dataList.html");
    }


    /**
     * res table data
     */
    public void tableData() throws ContractException {
        int pageNumber = getParaToInt("pageNumber", 1);
        int pageSize = getParaToInt("pageSize", 30);

        PrivateEntityDTO entityDTO=new PrivateEntityDTO();
        EntityDTO entity=entityDTO.getEntityDTO();
        entity.setUploader(getPara("name"));

        int total=0;
        int pages=0;
        Contract contract = getContract();
        List<EntityDTO> datalist=new ArrayList<>();

        String collection="";
        if(mspId.equals("Org1MSP")){
            collection="collectionOrg1Entities";
        }else if(mspId.equals("Org2MSP")){
            collection="collectionOrg2Entities";
        }

        if(StrKit.notBlank(entity.getUploader())){

            byte[] allEntities=contract.evaluateTransaction("queryPrivateEntityByUploader", entity.getUploader());
            datalist= StringToList.ByteToClassList_private(allEntities);

        }else{
            byte[] allEntities = contract.evaluateTransaction("queryAllPrivateEntities");
            datalist= StringToList.ByteToClassList_private(allEntities);
        }

        if(datalist.size()!=0){
            total=datalist.size();
            pages=(int)(total / (long)pageSize + (long)(total % (long)pageSize == 0L ? 0 : 1));
        }
        Page<EntityDTO> entityDTOPage = new Page<EntityDTO>(datalist,pageNumber,pageSize,pages,datalist.size());
//        Page<EntityDTO> entityDTOPage = entityService.findPage(entity,pageNumber,pageSize);

        renderJson(new DataTable<EntityDTO>(entityDTOPage));
    }

    public void upload() {
        String address=getIPAddress();
        System.out.println(address);


        List<User> userList =userService.findByStatusUsed();
        System.out.println("userList:"+userList);

//        String[] coope={"admin","Bobo","Breathe"};
//        List<String> coope_userList=new ArrayList<>(Arrays.asList(coope));

        setAttr("userList",userList).setAttr("address",address).render("dataUpload.html");
    }

    public void createEntity() throws ContractException, TimeoutException, InterruptedException {
        Map<String , Object> result = Maps.newConcurrentMap();
        Contract contract = getContract();

        String collection="";
        if(mspId.equals("Org1MSP")){
             collection="collectionOrg1Entities";
        }else if(mspId.equals("Org2MSP")){
            collection="collectionOrg2Entities";
        }

        String key=getPara("key");
        String content="";
        content+="Data set name:"+getPara("name")+"  Dataset description:"+getPara("des")+"  Data address:"+getPara("url")+"  access policy:"+getPara("role");
        System.out.println(content);
        String uploader=getIPAddress();


        String[] cooperator=getParaValues("userCooperator[]");
        String cooperator_str=String.join(",",cooperator);
        String[] downloader=getParaValues("userDownloader[]");
        String downloader_str=String.join(",",downloader);

        String sysUser=AuthUtils.getLoginUser().getName();

        String cid=getPara("cid");

        System.out.println(sysUser);
        System.out.println(cid);

//        ArrayList a= new ArrayList(Arrays.asList(cooperator));
//        String[] downloader=getParaValues("userDownloader[]");
//        ArrayList b= new ArrayList(Arrays.asList(downloader));
//        System.out.println(b);

        byte[] bytes = contract.submitTransaction("createPrivateEntity", collection,key,content,uploader,cooperator_str,downloader_str,sysUser,cid);

        result.put("payload" , StringUtils.newStringUtf8(bytes));
        result.put("status" , "ok");

        renderJson(result);
    }

    public void download() {
        String address = getIPAddress();
        if(address.equals("127.0.0.1")){
            address="192.168.32.1";
        }
        else{
            System.out.println("!=");
        }
        System.out.println(address);
        setAttr("address",address).render("dataDownload.html");
    }

    public void showdown() throws ContractException {
        String id=getPara("id");
        System.out.println(id);

        String collection="";
        if(mspId.equals("Org1MSP")){
            collection="collectionOrg1Entities";
        }else if(mspId.equals("Org2MSP")){
            collection="collectionOrg2Entities";
        }

        Contract contract = getContract();
        byte[] entity = contract.evaluateTransaction("queryPrivateEntity", id);
        EntityDTO entityDTO=StringToList.ByteToClass_private(entity);

        String url=entityDTO.getContent().substring(entityDTO.getContent().indexOf("Data address:")+5,entityDTO.getContent().indexOf("access policy:")-2);
        String des=entityDTO.getContent().substring(entityDTO.getContent().indexOf("Dataset description:")+6,entityDTO.getContent().indexOf("Data address:")-2);
        String name=entityDTO.getContent().substring(entityDTO.getContent().indexOf("Data set name:")+6,entityDTO.getContent().indexOf("Dataset description:")-2);

        setAttr("name",name)
                .setAttr("entity",entityDTO)
                .setAttr("des",des)
                .setAttr("url",url)
                .render("download.html");

    }

    public void update() throws ContractException{
        String id=getPara("id");

        String collection="";
        if(mspId.equals("Org1MSP")){
            collection="collectionOrg1Entities";
        }else if(mspId.equals("Org2MSP")){
            collection="collectionOrg2Entities";
        }

        Contract contract = getContract();
        byte[] entity = contract.evaluateTransaction("queryPrivateEntity", id);
        EntityDTO entityDTO=StringToList.ByteToClass_private(entity);

        List<User> userList =userService.findByStatusUsed();

        String url=entityDTO.getContent().substring(entityDTO.getContent().indexOf("Data address:")+5,entityDTO.getContent().indexOf("access policy:")-2);
        String des=entityDTO.getContent().substring(entityDTO.getContent().indexOf("Dataset description:")+6,entityDTO.getContent().indexOf("Data address:")-2);
        String name=entityDTO.getContent().substring(entityDTO.getContent().indexOf("Data set name:")+6,entityDTO.getContent().indexOf("Dataset description:")-2);

        List<String> cooperator=entityDTO.getCooperator();
        List<String> downloader=entityDTO.getDownloadPermission();
        String cid=entityDTO.getCID();

        setAttr("userList",userList)
                .setAttr("cooperator",cooperator)
                .setAttr("downloader",downloader)
                .setAttr("cid",cid)
                .setAttr("name",name)
                .setAttr("entity",entityDTO)
                .setAttr("des",des)
                .setAttr("url",url)
                .render("update.html");
    }

    public void postUpdate() throws ContractException, TimeoutException, InterruptedException {
        Map<String , Object> result = Maps.newConcurrentMap();
        Contract contract = getContract();

        String collection="";
        if(mspId.equals("Org1MSP")){
            collection="collectionOrg1Entities";
        }else if(mspId.equals("Org2MSP")){
            collection="collectionOrg2Entities";
        }

        String key=getPara("key");
        String content="";
        content+="Data set name:"+getPara("name")+"  Dataset description:"+getPara("des")+"  Data address:"+getPara("url")+"  access policy:"+getPara("role");
        String uploader=getIPAddress();

        String[] cooperator=getParaValues("userCooperator[]");
        String cooperator_str=String.join(",",cooperator);
        String[] downloader=getParaValues("userDownloader[]");
        String downloader_str=String.join(",",downloader);

        String sysUser=AuthUtils.getLoginUser().getName();

        String cid=getPara("cid");

        byte[] bytes = contract.submitTransaction("updatePrivateEntity",collection, key, content,uploader,cooperator_str,downloader_str,sysUser,cid);

        result.put("payload" , StringUtils.newStringUtf8(bytes));
        result.put("status" , "ok");

        renderJson(RestResult.buildSuccess());

    }

    public void updateUploader() throws ContractException{

        String collection="";
        if(mspId.equals("Org1MSP")){
            collection="collectionOrg1Entities";
        }else if(mspId.equals("Org2MSP")){
            collection="collectionOrg2Entities";
        }

        Contract contract = getContract();
        byte[] entity = contract.evaluateTransaction("queryPrivateEntity", "entity-0");
        EntityDTO entityDTO=StringToList.ByteToClass_private(entity);

        List<User> userList =userService.findByStatusUsed();


        List<String> uploader_permission=entityDTO.getUploadPermission();

        setAttr("userList",userList)
                .setAttr("uploader_permission",uploader_permission)
                .setAttr("entity",entityDTO)
                .render("updateUploader.html");
    }

    public void postUpdateUploader() throws ContractException, TimeoutException, InterruptedException {
        Map<String , Object> result = Maps.newConcurrentMap();
        Contract contract = getContract();

        String collection="";
        if(mspId.equals("Org1MSP")){
            collection="collectionOrg1Entities";
        }else if(mspId.equals("Org2MSP")){
            collection="collectionOrg2Entities";
        }

        String[] userUploader_permission=getParaValues("userUploader[]");
        String userUploader_list=String.join(",",userUploader_permission);

        String sysUser=AuthUtils.getLoginUser().getName();


        byte[] bytes = contract.submitTransaction("updatePrivateUpload",collection,userUploader_list,sysUser);

        result.put("payload" , StringUtils.newStringUtf8(bytes));
        result.put("status" , "ok");

        renderJson(RestResult.buildSuccess());

    }

    public void updateCooperator() throws ContractException{
        String id=getPara("id");

        Contract contract = getContract();

        String collection="";
        if(mspId.equals("Org1MSP")){
            collection="collectionOrg1Entities";
        }else if(mspId.equals("Org2MSP")){
            collection="collectionOrg2Entities";
        }

        byte[] entity = contract.evaluateTransaction("queryPrivateEntity", id);
        EntityDTO entityDTO=StringToList.ByteToClass_private(entity);

        List<User> userList =userService.findByStatusUsed();

        String url=entityDTO.getContent().substring(entityDTO.getContent().indexOf("Data address:")+5,entityDTO.getContent().indexOf("access policy:")-2);
        String des=entityDTO.getContent().substring(entityDTO.getContent().indexOf("Dataset description:")+6,entityDTO.getContent().indexOf("Data address:")-2);
        String name=entityDTO.getContent().substring(entityDTO.getContent().indexOf("Data set name:")+6,entityDTO.getContent().indexOf("Dataset description:")-2);

        List<String> cooperator=entityDTO.getCooperator();
        List<String> downloader=entityDTO.getDownloadPermission();
        String cid=entityDTO.getCID();

        setAttr("userList",userList)
                .setAttr("cooperator",cooperator)
                .setAttr("downloader",downloader)
                .setAttr("cid",cid)
                .setAttr("name",name)
                .setAttr("entity",entityDTO)
                .setAttr("des",des)
                .setAttr("url",url)
                .render("updateCooperator.html");
    }

    public void postUpdateCooperator() throws ContractException, TimeoutException, InterruptedException {
        Map<String , Object> result = Maps.newConcurrentMap();
        Contract contract = getContract();

        String key=getPara("key");
        String content="";
        content+="Data set name:"+getPara("name")+"  Dataset description:"+getPara("des")+"  Data address:"+getPara("url")+"  access policy:"+getPara("role");
        String uploader=getIPAddress();

        String collection="";
        if(mspId.equals("Org1MSP")){
            collection="collectionOrg1Entities";
        }else if(mspId.equals("Org2MSP")){
            collection="collectionOrg2Entities";
        }

        String[] cooperator=getParaValues("userCooperator[]");
        String cooperator_str=String.join(",",cooperator);
        String[] downloader=getParaValues("userDownloader[]");
        String downloader_str=String.join(",",downloader);

        String sysUser=AuthUtils.getLoginUser().getName();

        String cid=getPara("cid");

        byte[] bytes = contract.submitTransaction("updatePrivateCooperator",collection,key, content,uploader,cooperator_str,downloader_str,sysUser,cid);

        result.put("payload" , StringUtils.newStringUtf8(bytes));
        result.put("status" , "ok");

        renderJson(RestResult.buildSuccess());

    }

    public void updateDownloader() throws ContractException{
        String id=getPara("id");

        Contract contract = getContract();

        String collection="";
        if(mspId.equals("Org1MSP")){
            collection="collectionOrg1Entities";
        }else if(mspId.equals("Org2MSP")){
            collection="collectionOrg2Entities";
        }

        byte[] entity = contract.evaluateTransaction("queryPrivateEntity", id);
        EntityDTO entityDTO=StringToList.ByteToClass_private(entity);

        List<User> userList =userService.findByStatusUsed();

        String url=entityDTO.getContent().substring(entityDTO.getContent().indexOf("Data address:")+5,entityDTO.getContent().indexOf("access policy:")-2);
        String des=entityDTO.getContent().substring(entityDTO.getContent().indexOf("Dataset description:")+6,entityDTO.getContent().indexOf("Data address:")-2);
        String name=entityDTO.getContent().substring(entityDTO.getContent().indexOf("Data set name:")+6,entityDTO.getContent().indexOf("Dataset description:")-2);

        List<String> cooperator=entityDTO.getCooperator();
        List<String> downloader=entityDTO.getDownloadPermission();
        String cid=entityDTO.getCID();

        setAttr("userList",userList)
                .setAttr("cooperator",cooperator)
                .setAttr("downloader",downloader)
                .setAttr("cid",cid)
                .setAttr("name",name)
                .setAttr("entity",entityDTO)
                .setAttr("des",des)
                .setAttr("url",url)
                .render("updateDownloader.html");
    }

    public void postUpdateDownloader() throws ContractException, TimeoutException, InterruptedException {
        Map<String , Object> result = Maps.newConcurrentMap();
        Contract contract = getContract();

        String key=getPara("key");
        String content="";
        content+="Data set name:"+getPara("name")+"  Dataset description:"+getPara("des")+"  Data address:"+getPara("url")+"  access policy:"+getPara("role");
        String uploader=getIPAddress();

        String collection="";
        if(mspId.equals("Org1MSP")){
            collection="collectionOrg1Entities";
        }else if(mspId.equals("Org2MSP")){
            collection="collectionOrg2Entities";
        }

        String[] cooperator=getParaValues("userCooperator[]");
        String cooperator_str=String.join(",",cooperator);
        String[] downloader=getParaValues("userDownloader[]");
        String downloader_str=String.join(",",downloader);

        String sysUser=AuthUtils.getLoginUser().getName();

        String cid=getPara("cid");

        byte[] bytes = contract.submitTransaction("updatePrivateDownload", collection,key, content,uploader,cooperator_str,downloader_str,sysUser,cid);

        result.put("payload" , StringUtils.newStringUtf8(bytes));
        result.put("status" , "ok");

        renderJson(RestResult.buildSuccess());

    }

    public void delete() throws InterruptedException, TimeoutException, ContractException {
        Map<String , Object> result = Maps.newConcurrentMap();
        Contract contract = getContract();

        String key=getPara("id");

        String sysUser=AuthUtils.getLoginUser().getName();

        String collection="";
        if(mspId.equals("Org1MSP")){
            collection="collectionOrg1Entities";
        }else if(mspId.equals("Org2MSP")){
            collection="collectionOrg2Entities";
        }

        byte[] entity = contract.createTransaction("deletePrivateEntity")
                .setEndorsingPeers(gateway.getNetwork("businesschannel").getChannel().getPeers(EnumSet.of(Peer.PeerRole.ENDORSING_PEER)))
                .submit(collection,key,sysUser);

        result.put("payload" , StringUtils.newStringUtf8(entity));
        result.put("status" , "ok");

        renderJson(RestResult.buildSuccess());
    }

    public void IPFSUpload() throws IOException {

        //get ipfs connection
        IPFS ipfs = new IPFS("/ip4/192.168.32.1/tcp/5001");

        //  filePath refers to the path of the uploaded file
        String filePath=getPara("url");
        System.out.println(filePath);

//      String filePath = "C:\\Users\\LENOVO\\Desktop\\test.txt";

        String cid = IPFSUtil.upload(ipfs, filePath);

        renderText(cid);
    }

    private Contract getContract() {

        // get channel
        Network network = gateway.getNetwork("businesschannel");

        // get contract
        return network.getContract("hyperledger-fabric-contract-java-datashare");
    }

}
