package com.sltx.controller.system;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Page;
import com.sltx.common.base.BaseController;
import com.sltx.common.exception.BusinessException;
import com.sltx.common.model.DataTable;
import com.sltx.common.model.RestResult;
import com.sltx.common.utils.AuthUtils;
import com.sltx.common.utils.NotNullPara;
import com.sltx.entity.model.*;
import com.sltx.entity.status.UserStatus;
import com.sltx.service.api.*;
import com.sltx.validator.ChangePwdValidator;
import com.sltx.util.TOTPUtil;


import io.jboot.web.controller.annotation.RequestMapping;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.inject.Inject;

/**
 * System User Management
 * @author Rlax
 * 
 */
@RequestMapping("/system/user")
public class UserController extends BaseController {


	//@JbootrpcService
    @Inject
    private UserService userService;

    //@JbootrpcService
    @Inject
    private RoleService roleService;


    /**
     * index
     */
    public void index() {
        render("main.html");
    }

    /**
     * res table data
     */
    public void tableData() {
        int pageNumber = getParaToInt("pageNumber", 1);
        int pageSize = getParaToInt("pageSize", 30);

        User sysUser = new User();
        sysUser.setName(getPara("name"));
        sysUser.setPhone(getPara("phone"));

        Page<User> userPage = userService.findPage(sysUser, pageNumber, pageSize);
        renderJson(new DataTable<User>(userPage));
    }

    /**
     * add
     */
    public void add() {
        List<Role> roleList = roleService.findByStatusUsed();
        setAttr("roleList", roleList).render("add.html");
    }

    public void postAdd() {
        User sysUser = getBean(User.class, "user");
        Long[] roles = getParaValuesToLong("userRole");
        System.out.println(sysUser.getName());
        System.out.println(roles[0]);

        if (userService.hasUser(sysUser.getName())) {
            throw new BusinessException("username already exists");
        }

        sysUser.setLastUpdAcct(AuthUtils.getLoginUser().getName());
        if (!userService.saveUser(sysUser, roles)) {
            throw new BusinessException("Save failed");
        }

        renderJson(RestResult.buildSuccess());
    }

    /**
     * update
     */
    @NotNullPara({"id"})
    public void update() {
        Long id = getParaToLong("id");
        User sysUser = userService.findById(id);

        List<Role> roleList = roleService.findByStatusUsed();
        System.out.println(roleList);
        List<Role> sysRoleList = roleService.findByUserName(sysUser.getName());
        System.out.println(sysRoleList);

        setAttr("user", sysUser).setAttr("roleList", roleList).setAttr("userRoleList", sysRoleList).render("update.html");
    }

    public void postUpdate() {
        User sysUser = getBean(User.class, "user");
        Long[] roles = getParaValuesToLong("userRole");

        User _sysUser = userService.findById(sysUser.getId());
        if (_sysUser == null) {
            throw new BusinessException("User does not exist");
        }

        sysUser.setLastUpdAcct(AuthUtils.getLoginUser().getName());

        if (!userService.updateUser(sysUser, roles)) {
            throw new BusinessException("fail to edit");
        }
        renderJson(RestResult.buildSuccess());
    }

    @NotNullPara({"id"})
    public void delete() {
        Long id = getParaToLong("id");
        if (!userService.deleteById(id)) {
            throw new BusinessException("failed to delete");
        }

        renderJson(RestResult.buildSuccess());
    }

    @NotNullPara({"id"})
    public void use() {
        Long id = getParaToLong("id");

        User sysUser = userService.findById(id);
        if (sysUser == null) {
            throw new BusinessException("User does not exist");
        }

        sysUser.setStatus(UserStatus.USED);
        sysUser.setLastUpdTime(new Date());
        sysUser.setNote("Unlock system user");

        if (!userService.update(sysUser)) {
            throw new BusinessException("Unlock failed");
        }

        renderJson(RestResult.buildSuccess());
    }

    @NotNullPara({"id"})
    public void unuse() {
        Long id = getParaToLong("id");

        User sysUser = userService.findById(id);
        if (sysUser == null) {
            throw new BusinessException("User does not exist");
        }

        sysUser.setStatus(UserStatus.LOCKED);
        sysUser.setLastUpdTime(new Date());
        sysUser.setNote("Lock system user");

        if (!userService.update(sysUser)) {
            throw new BusinessException("Lock failed");
        }

        renderJson(RestResult.buildSuccess());
    }

    public void profile() {
        User sysUser = userService.findById(AuthUtils.getLoginUser().getId());
        setAttr("user", sysUser).render("profile.html");
    }

    public void postProfile() {
        User sysUser = getBean(User.class, "user");
        if (!sysUser.getId().equals(AuthUtils.getLoginUser().getId())) {
            throw new BusinessException("No permission to operate");
        }

        sysUser.setLastUpdAcct(AuthUtils.getLoginUser().getName());
        sysUser.setLastUpdTime(new Date());
        sysUser.setNote("User modifies personal information");

        if (!userService.update(sysUser)) {
            throw new BusinessException("Data modification failed");
        }

        renderJson(RestResult.buildSuccess());
    }

    public void changepwd() {
        User sysUser = AuthUtils.getLoginUser();
        setAttr("user", sysUser).render("changepwd.html");
    }

    @Before( {POST.class, ChangePwdValidator.class} )
    public void postChangepwd() {
        User sysUser = getBean(User.class, "user");
        if (!sysUser.getId().equals(AuthUtils.getLoginUser().getId())) {
            throw new BusinessException("Not authorized to operate");
        }

        String pwd = getPara("newPwd");


        String salt2 = new SecureRandomNumberGenerator().nextBytes().toHex();
        SimpleHash hash = new SimpleHash("md5", pwd, salt2, 2);
        pwd = hash.toHex();
        sysUser.setPwd(pwd);
        sysUser.setSalt2(salt2);
        sysUser.setLastUpdAcct(AuthUtils.getLoginUser().getName());
        sysUser.setLastUpdTime(new Date());
        sysUser.setNote("user change password");

        if (!userService.update(sysUser)) {
            throw new BusinessException("Failed to change password");
        }

        renderJson(RestResult.buildSuccess());
    }

    /**
     * Open the client socket to send a connection request to the data owner
     */
    public void socketClient() {
        try {

            String opeAddress=getIPAddress();
            if(opeAddress.equals("127.0.0.1")){
                System.out.println("==");
                opeAddress="192.168.32.1";
            }else{
                System.out.println("!=");
            }

            String downfile=getPara("name");


            String ipAddress=getPara("ipAddress");

            int port=Integer.valueOf(getPara("port"));

            Socket sock=new Socket(InetAddress.getByName(ipAddress),port);

//            ServerSocket ss = new ServerSocket();
//            ss.bind(new InetSocketAddress(opeAddress, 4711));
            try (
//                    Socket sock = ss.accept();
                    InputStream in = sock.getInputStream();
                    OutputStream out = new BufferedOutputStream(new FileOutputStream(downfile.substring(downfile.lastIndexOf("\\")+1)));
            ) {

                // Seed for HMAC-SHA256 - 32 bytes
                String seed32 = "3132333435363738393031323334353637383930"
                        + "313233343536373839303132";

                long T0 = 0;
                long X = 30;
                String steps = "0";

                long T = (new Date().getTime() - T0) / X;
                steps = Long.toHexString(T).toUpperCase();
                while (steps.length() < 16)
                    steps = "0" + steps;

                    System.out.println(TOTPUtil.generateTOTP256(seed32,steps,"8"));

                /**TOTO dynatic secret**/
                Integer secret=Integer.valueOf(TOTPUtil.generateTOTP256(seed32,steps,"8"));

                int data=-1;
                int totalBytes=0;
                while ((data=in.read())>-1){
                    totalBytes+=data;
                    out.write(data-secret);
                }

                int bytesCopied = totalBytes;
                System.out.println(String.format("%d bytes received", bytesCopied));


//                sock.close();


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        renderText("Data transfer successful");
    }
}
