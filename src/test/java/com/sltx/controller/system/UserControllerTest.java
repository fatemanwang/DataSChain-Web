package com.sltx.controller.system;

import com.jfinal.plugin.activerecord.Page;
import com.sltx.common.exception.BusinessException;
import com.sltx.entity.model.Role;
import com.sltx.entity.model.User;
import com.sltx.service.api.RoleService;
import com.sltx.service.api.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    private UserService mockUserService;
    @Mock
    private RoleService mockRoleService;

    @InjectMocks
    private UserController userControllerUnderTest;

    @Test
    public void testIndex() {
        // Setup
        // Run the test
        userControllerUnderTest.index();

        // Verify the results
    }

    @Test
    public void testTableData() {
        // Setup
        // Configure UserService.findPage(...).
        final User user = new User();
        final Page<User> userPage = new Page<>(Arrays.asList(user), 0, 0, 0, 0);
        final User sysUser = new User();
        when(mockUserService.findPage(sysUser, 0, 0)).thenReturn(userPage);

        // Run the test
        userControllerUnderTest.tableData();

        // Verify the results
    }

    @Test
    public void testAdd() {
        // Setup
        // Configure RoleService.findByStatusUsed(...).
        final Role role = new Role();
        final List<Role> roles = Arrays.asList(role);
        when(mockRoleService.findByStatusUsed()).thenReturn(roles);

        // Run the test
        userControllerUnderTest.add();

        // Verify the results
    }

    @Test
    public void testAdd_RoleServiceReturnsNoItems() {
        // Setup
        when(mockRoleService.findByStatusUsed()).thenReturn(Collections.emptyList());

        // Run the test
        userControllerUnderTest.add();

        // Verify the results
    }

    @Test
    public void testPostAdd() {
        // Setup
        when(mockUserService.hasUser("name")).thenReturn(false);

        // Configure UserService.saveUser(...).
        final User user = new User();
        when(mockUserService.saveUser(eq(user), any(Long[].class))).thenReturn(true);

        // Run the test
        userControllerUnderTest.postAdd();

        // Verify the results
    }

    @Test(expected = BusinessException.class)
    public void testPostAdd_UserServiceHasUserReturnsTrue() {
        // Setup
        when(mockUserService.hasUser("name")).thenReturn(true);

        // Run the test
        userControllerUnderTest.postAdd();
    }

    @Test(expected = BusinessException.class)
    public void testPostAdd_UserServiceSaveUserReturnsFalse() {
        // Setup
        when(mockUserService.hasUser("name")).thenReturn(false);

        // Configure UserService.saveUser(...).
        final User user = new User();
        when(mockUserService.saveUser(eq(user), any(Long[].class))).thenReturn(false);

        // Run the test
        userControllerUnderTest.postAdd();
    }

    @Test
    public void testUpdate() {
        // Setup
        // Configure UserService.findById(...).
        final User user = new User();
        when(mockUserService.findById(0L)).thenReturn(user);

        // Configure RoleService.findByStatusUsed(...).
        final Role role = new Role();
        final List<Role> roles = Arrays.asList(role);
        when(mockRoleService.findByStatusUsed()).thenReturn(roles);

        // Configure RoleService.findByUserName(...).
        final Role role1 = new Role();
        final List<Role> roles1 = Arrays.asList(role1);
        when(mockRoleService.findByUserName("name")).thenReturn(roles1);

        // Run the test
        userControllerUnderTest.update();

        // Verify the results
    }

    @Test
    public void testUpdate_RoleServiceFindByStatusUsedReturnsNoItems() {
        // Setup
        // Configure UserService.findById(...).
        final User user = new User();
        when(mockUserService.findById(0L)).thenReturn(user);

        when(mockRoleService.findByStatusUsed()).thenReturn(Collections.emptyList());

        // Configure RoleService.findByUserName(...).
        final Role role = new Role();
        final List<Role> roles = Arrays.asList(role);
        when(mockRoleService.findByUserName("name")).thenReturn(roles);

        // Run the test
        userControllerUnderTest.update();

        // Verify the results
    }

    @Test
    public void testUpdate_RoleServiceFindByUserNameReturnsNoItems() {
        // Setup
        // Configure UserService.findById(...).
        final User user = new User();
        when(mockUserService.findById(0L)).thenReturn(user);

        // Configure RoleService.findByStatusUsed(...).
        final Role role = new Role();
        final List<Role> roles = Arrays.asList(role);
        when(mockRoleService.findByStatusUsed()).thenReturn(roles);

        when(mockRoleService.findByUserName("name")).thenReturn(Collections.emptyList());

        // Run the test
        userControllerUnderTest.update();

        // Verify the results
    }

    @Test
    public void testPostUpdate() {
        // Setup
        // Configure UserService.findById(...).
        final User user = new User();
        when(mockUserService.findById(0L)).thenReturn(user);

        // Configure UserService.updateUser(...).
        final User user1 = new User();
        when(mockUserService.updateUser(eq(user1), any(Long[].class))).thenReturn(true);

        // Run the test
        userControllerUnderTest.postUpdate();

        // Verify the results
    }

    @Test(expected = BusinessException.class)
    public void testPostUpdate_UserServiceFindByIdReturnsNull() {
        // Setup
        when(mockUserService.findById(0L)).thenReturn(null);

        // Run the test
        userControllerUnderTest.postUpdate();
    }

    @Test(expected = BusinessException.class)
    public void testPostUpdate_UserServiceUpdateUserReturnsFalse() {
        // Setup
        // Configure UserService.findById(...).
        final User user = new User();
        when(mockUserService.findById(0L)).thenReturn(user);

        // Configure UserService.updateUser(...).
        final User user1 = new User();
        when(mockUserService.updateUser(eq(user1), any(Long[].class))).thenReturn(false);

        // Run the test
        userControllerUnderTest.postUpdate();
    }

    @Test
    public void testDelete() {
        // Setup
        when(mockUserService.deleteById(0L)).thenReturn(true);

        // Run the test
        userControllerUnderTest.delete();

        // Verify the results
    }

    @Test(expected = BusinessException.class)
    public void testDelete_UserServiceReturnsFalse() {
        // Setup
        when(mockUserService.deleteById(0L)).thenReturn(false);

        // Run the test
        userControllerUnderTest.delete();
    }

    @Test
    public void testUse() {
        // Setup
        // Configure UserService.findById(...).
        final User user = new User();
        when(mockUserService.findById(0L)).thenReturn(user);

        // Configure UserService.update(...).
        final User model = new User();
        when(mockUserService.update(model)).thenReturn(true);

        // Run the test
        userControllerUnderTest.use();

        // Verify the results
    }

    @Test(expected = BusinessException.class)
    public void testUse_UserServiceFindByIdReturnsNull() {
        // Setup
        when(mockUserService.findById(0L)).thenReturn(null);

        // Run the test
        userControllerUnderTest.use();
    }

    @Test(expected = BusinessException.class)
    public void testUse_UserServiceUpdateReturnsFalse() {
        // Setup
        // Configure UserService.findById(...).
        final User user = new User();
        when(mockUserService.findById(0L)).thenReturn(user);

        // Configure UserService.update(...).
        final User model = new User();
        when(mockUserService.update(model)).thenReturn(false);

        // Run the test
        userControllerUnderTest.use();
    }

    @Test
    public void testUnuse() {
        // Setup
        // Configure UserService.findById(...).
        final User user = new User();
        when(mockUserService.findById(0L)).thenReturn(user);

        // Configure UserService.update(...).
        final User model = new User();
        when(mockUserService.update(model)).thenReturn(true);

        // Run the test
        userControllerUnderTest.unuse();

        // Verify the results
    }

    @Test(expected = BusinessException.class)
    public void testUnuse_UserServiceFindByIdReturnsNull() {
        // Setup
        when(mockUserService.findById(0L)).thenReturn(null);

        // Run the test
        userControllerUnderTest.unuse();
    }

    @Test(expected = BusinessException.class)
    public void testUnuse_UserServiceUpdateReturnsFalse() {
        // Setup
        // Configure UserService.findById(...).
        final User user = new User();
        when(mockUserService.findById(0L)).thenReturn(user);

        // Configure UserService.update(...).
        final User model = new User();
        when(mockUserService.update(model)).thenReturn(false);

        // Run the test
        userControllerUnderTest.unuse();
    }

    @Test
    public void testProfile() {
        // Setup
        // Configure UserService.findById(...).
        final User user = new User();
        when(mockUserService.findById(0L)).thenReturn(user);

        // Run the test
        userControllerUnderTest.profile();

        // Verify the results
    }

    @Test
    public void testPostProfile() {
        // Setup
        // Configure UserService.update(...).
        final User model = new User();
        when(mockUserService.update(model)).thenReturn(true);

        // Run the test
        userControllerUnderTest.postProfile();

        // Verify the results
    }

    @Test(expected = BusinessException.class)
    public void testPostProfile_UserServiceReturnsFalse() {
        // Setup
        // Configure UserService.update(...).
        final User model = new User();
        when(mockUserService.update(model)).thenReturn(false);

        // Run the test
        userControllerUnderTest.postProfile();
    }

    @Test
    public void testChangepwd() {
        // Setup
        // Run the test
        userControllerUnderTest.changepwd();

        // Verify the results
    }

    @Test
    public void testPostChangepwd() {
        // Setup
        // Configure UserService.update(...).
        final User model = new User();
        when(mockUserService.update(model)).thenReturn(true);

        // Run the test
        userControllerUnderTest.postChangepwd();

        // Verify the results
    }

    @Test(expected = BusinessException.class)
    public void testPostChangepwd_UserServiceReturnsFalse() {
        // Setup
        // Configure UserService.update(...).
        final User model = new User();
        when(mockUserService.update(model)).thenReturn(false);

        // Run the test
        userControllerUnderTest.postChangepwd();
    }

    @Test
    public void testSocketClient() {
        // Setup
        // Run the test
        userControllerUnderTest.socketClient();

        // Verify the results
    }
}
