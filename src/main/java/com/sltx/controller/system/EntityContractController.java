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

@RequestMapping("/system/entity")
public class EntityContractController extends BaseController {


//    @Inject
//    private EntityService entityService;

    final String sysUser = AuthUtils.getLoginUser().getName();
    final String mspId=  AuthUtils.getLoginUser().getMspId();

    final Gateway gateway= new HyperLedgerFabricConfig().gateway(mspId);


    @Inject
    private RoleService roleService;

    @Inject
    private UserService userService;

    public EntityContractController() throws Exception {
    }

    /**
     * index
     */
    public void index(){
        render("dataList.html");
    }


    /**
     * rest table Data
     */
    public void tableData() throws ContractException {
        int pageNumber = getParaToInt("pageNumber", 1);
        int pageSize = getParaToInt("pageSize", 30);

        EntityDTO entity=new EntityDTO();
        entity.setUploader(getPara("name"));

        int total=0;
        int pages=0;
        Contract contract = getContract();
        List<EntityDTO> datalist=new ArrayList<>();

        if(StrKit.notBlank(entity.getUploader())){

            byte[] allEntities=contract.evaluateTransaction("queryEntityByUploader", entity.getUploader());
            datalist= StringToList.ByteToClassList(allEntities);

        }else{
            byte[] allEntities = contract.evaluateTransaction("queryAllEntities_2");
            datalist= StringToList.ByteToClassList(allEntities);
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
        if(address.equals("127.0.0.1")){
            System.out.println("==");
            address="192.168.32.1";
        }
        else{
            System.out.println("!=");
        }

        List<User> userList =userService.findByStatusUsed();
        System.out.println("userList:"+userList);

//        String[] coope={"admin","Bobo","Breathe"};
//        List<String> coope_userList=new ArrayList<>(Arrays.asList(coope));

        setAttr("userList",userList).setAttr("address",address).render("dataUpload.html");
    }

    public void createEntity() throws ContractException, TimeoutException, InterruptedException {
        Map<String , Object> result = Maps.newConcurrentMap();
        Contract contract = getContract();

        String key=getPara("key");
        String content="";
        content+="Data set name:"+getPara("name")+"  Dataset description:"+getPara("des")+"  Data address:"+getPara("url")+"  access policy:"+getPara("role");
        System.out.println(content);
        String uploader=getIPAddress();
        if(uploader.equals("127.0.0.1")){
            System.out.println("==");
            uploader="192.168.32.1";
        }
        else{
            System.out.println("!=");
        }

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

        byte[] bytes = contract.submitTransaction("createEntity", key,content,uploader,cooperator_str,downloader_str,sysUser,cid);

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

        Contract contract = getContract();
        byte[] entity = contract.evaluateTransaction("queryEntity", id);
        EntityDTO entityDTO=StringToList.ByteToClass(entity);

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

        Contract contract = getContract();
        byte[] entity = contract.evaluateTransaction("queryEntity", id);
        EntityDTO entityDTO=StringToList.ByteToClass(entity);

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

        String key=getPara("key");
        String content="";
        content+="Data set name:"+getPara("name")+"  Dataset description:"+getPara("des")+"  Data address:"+getPara("url")+"  access policy:"+getPara("role");
        String uploader=getIPAddress();
        if(uploader.equals("127.0.0.1")){
            System.out.println("equal");
            uploader="192.168.32.1";
        }
        else{
            System.out.println("not equal");
        }

        String[] cooperator=getParaValues("userCooperator[]");
        String cooperator_str=String.join(",",cooperator);
        String[] downloader=getParaValues("userDownloader[]");
        String downloader_str=String.join(",",downloader);

        String sysUser=AuthUtils.getLoginUser().getName();

        String cid=getPara("cid");

        byte[] bytes = contract.submitTransaction("updateEntity", key, content,uploader,cooperator_str,downloader_str,sysUser,cid);

        result.put("payload" , StringUtils.newStringUtf8(bytes));
        result.put("status" , "ok");

        renderJson(RestResult.buildSuccess());

    }

    public void updateUploader() throws ContractException{

        Contract contract = getContract();
        byte[] entity = contract.evaluateTransaction("queryEntity", "entity-0");
        EntityDTO entityDTO=StringToList.ByteToClass(entity);

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


        String[] userUploader_permission=getParaValues("userUploader[]");
        String userUploader_list=String.join(",",userUploader_permission);

        String sysUser=AuthUtils.getLoginUser().getName();


        byte[] bytes = contract.submitTransaction("updateUpload",userUploader_list,sysUser);

        result.put("payload" , StringUtils.newStringUtf8(bytes));
        result.put("status" , "ok");

        renderJson(RestResult.buildSuccess());

    }

    public void updateCooperator() throws ContractException{
        String id=getPara("id");

        Contract contract = getContract();
        byte[] entity = contract.evaluateTransaction("queryEntity", id);
        EntityDTO entityDTO=StringToList.ByteToClass(entity);

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
        if(uploader.equals("127.0.0.1")){
            System.out.println("equal");
            uploader="192.168.32.1";
        }
        else{
            System.out.println("not equal");
        }

        String[] cooperator=getParaValues("userCooperator[]");
        String cooperator_str=String.join(",",cooperator);
        String[] downloader=getParaValues("userDownloader[]");
        String downloader_str=String.join(",",downloader);

        String sysUser=AuthUtils.getLoginUser().getName();

        String cid=getPara("cid");

        byte[] bytes = contract.submitTransaction("updateCooperator", key, content,uploader,cooperator_str,downloader_str,sysUser,cid);

        result.put("payload" , StringUtils.newStringUtf8(bytes));
        result.put("status" , "ok");

        renderJson(RestResult.buildSuccess());

    }

    public void updateDownloader() throws ContractException{
        String id=getPara("id");

        Contract contract = getContract();
        byte[] entity = contract.evaluateTransaction("queryEntity", id);
        EntityDTO entityDTO=StringToList.ByteToClass(entity);

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

        String[] cooperator=getParaValues("userCooperator[]");
        String cooperator_str=String.join(",",cooperator);
        String[] downloader=getParaValues("userDownloader[]");
        String downloader_str=String.join(",",downloader);

        String sysUser=AuthUtils.getLoginUser().getName();

        String cid=getPara("cid");

        byte[] bytes = contract.submitTransaction("updateDownload", key, content,uploader,cooperator_str,downloader_str,sysUser,cid);

        result.put("payload" , StringUtils.newStringUtf8(bytes));
        result.put("status" , "ok");

        renderJson(RestResult.buildSuccess());

    }

    public void delete() throws InterruptedException, TimeoutException, ContractException {
        Map<String , Object> result = Maps.newConcurrentMap();
        Contract contract = getContract();

        String key=getPara("id");

        String sysUser=AuthUtils.getLoginUser().getName();

        /*
        * Change the endorsement policy,
        * the default endorsement node will be deleted when half of the endorsement nodes agree;
        * now all endorsement nodes need to agree to delete
        * */
        byte[] entity = contract.createTransaction("deleteEntity")
                .setEndorsingPeers(gateway.getNetwork("businesschannel").getChannel().getPeers(EnumSet.of(Peer.PeerRole.ENDORSING_PEER)))
                .submit(key,sysUser);

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


        String cid = IPFSUtil.upload(ipfs, filePath);

        renderText(cid);
    }

    public void IPFSTest() throws IOException {

        IPFS ipfs = new IPFS("/ip4/192.168.32.1/tcp/5001");

        for (int i = 1; i <=9; i++) {
            String filePath = "C:\\Users\\LENOVO\\Desktop\\test\\test"+i*100+".rar";

            Long pretime=System.currentTimeMillis();
            String cid = IPFSUtil.upload(ipfs, filePath);
            Long nowtime=System.currentTimeMillis();
            System.out.println("upload"+i*100+"M time taken for files to IPFS:"+(nowtime-pretime));

            renderText(cid);
        }


    }

    private Contract getContract() {

        // get channel
        Network network = gateway.getNetwork("businesschannel");

        // get contract
        return network.getContract("hyperledger-fabric-contract-java-datashare");
    }

}
