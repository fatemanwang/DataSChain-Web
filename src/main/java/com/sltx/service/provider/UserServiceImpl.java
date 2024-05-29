package com.sltx.service.provider;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.sltx.entity.model.User;
import com.sltx.entity.model.UserRole;
import com.sltx.entity.status.UserOnlineStatus;
import com.sltx.entity.status.UserStatus;
import com.sltx.service.api.UserRoleService;

import io.jboot.aop.annotation.Bean;

import com.sltx.service.api.UserService;

import io.jboot.core.rpc.annotation.JbootrpcService;
import io.jboot.db.model.Columns;
import io.jboot.service.JbootServiceBase;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Bean
@Singleton
//@JbootrpcService
public class UserServiceImpl extends JbootServiceBase<User> implements UserService {

    @Inject
    private UserRoleService userRoleService;

    @Override
    public Page<User> findPage(User user, int pageNumber, int pageSize) {
        Columns columns = Columns.create();

        if (StrKit.notBlank(user.getName())) {
            columns.like("name", "%"+user.getName()+"%");
        }
        if (StrKit.notBlank(user.getPhone())) {
            columns.like("phone", "%"+user.getPhone()+"%");
        }
        return DAO.paginateByColumns(pageNumber, pageSize, columns.getList(), "id desc");
    }

    @Override
    public boolean hasUser(String name) {
        return findByName(name) != null;
    }
    
    @Override
    public User findById(Object id) {
        return DAO.findFirstByColumn("id", id);
    }

    @Override
    public List<User> findByStatusUsed() {
        return  DAO.findListByColumn("status", UserStatus.USED);
    }

    @Override
    public User findByName(String name) {

        return DAO.findFirstByColumn("name", name);
    }

    @Override
    public boolean saveUser(User user, Long[] roles) {
        String pwd = user.getPwd();

        if (StrKit.notBlank(pwd)) {
            String salt2 = new SecureRandomNumberGenerator().nextBytes().toHex();
            SimpleHash hash = new SimpleHash("md5", pwd, salt2, 2);
            pwd = hash.toHex();
            user.setPwd(pwd);
            user.setSalt2(salt2);
        }

        user.setOnlineStatus(UserOnlineStatus.OFFLINE);
        user.setCreatedate(new Date());
        user.setLastUpdTime(new Date());
        user.setNote("self-created user");

        return Db.tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                if (!user.save()) {
                    return false;
                }

                if (roles != null) {
                    List<UserRole> list = new ArrayList<UserRole>();
                    for (Long roleId : roles) {
                        UserRole userRole = new UserRole();
                        userRole.setUserId(user.getId());
                        userRole.setRoleId(roleId);
                        list.add(userRole);
                    }
                    int[] rets = userRoleService.batchSave(list);

                    for (int ret : rets) {
                        if (ret < 1) {
                            return false;
                        }
                    }
                }
                return true;
            }
        });
    }

    @Override
    public boolean updateUser(User user, Long[] roles) {
        String pwd = user.getPwd();
        if (StrKit.notBlank(pwd)) {
            String salt2 = new SecureRandomNumberGenerator().nextBytes().toHex();
            SimpleHash hash = new SimpleHash("md5", pwd, salt2, 2);
            pwd = hash.toHex();
            user.setPwd(pwd);
            user.setSalt2(salt2);
        } else {
            user.remove("pwd");
        }

        user.setLastUpdTime(new Date());
        user.setNote("No remarks");

        return Db.tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                if (!user.update()) {
                    return false;
                }

                userRoleService.deleteByUserId(user.getId());

                if (roles != null) {
                    List<UserRole> list = new ArrayList<UserRole>();
                    for (Long roleId : roles) {
                        UserRole userRole = new UserRole();
                        userRole.setUserId(user.getId());
                        userRole.setRoleId(roleId);
                        list.add(userRole);
                    }

                    int[] rets = userRoleService.batchSave(list);
                    for (int ret : rets) {
                        if (ret < 1) {
                            return false;
                        }
                    }
                }
                return true;
            }
        });
    }
}