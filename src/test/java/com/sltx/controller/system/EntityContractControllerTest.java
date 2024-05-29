package com.sltx.controller.system;

import com.sltx.entity.model.User;
import com.sltx.service.api.RoleService;
import com.sltx.service.api.UserService;
import org.hyperledger.fabric.gateway.ContractException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EntityContractControllerTest {

    @Mock
    private RoleService mockRoleService;
    @Mock
    private UserService mockUserService;

    @InjectMocks
    private EntityContractController entityContractControllerUnderTest;

    @Test
    public void testIndex() {
        // Setup
        // Run the test
        entityContractControllerUnderTest.index();

        // Verify the results
    }

    @Test
    public void testTableData() throws Exception {
        // Setup
        // Run the test
        entityContractControllerUnderTest.tableData();

        // Verify the results
    }

    @Test(expected = ContractException.class)
    public void testTableData_ThrowsContractException() throws Exception {
        // Setup
        // Run the test
        entityContractControllerUnderTest.tableData();
    }

    @Test
    public void testUpload() {
        // Setup
        // Configure UserService.findByStatusUsed(...).
        final User user = new User();
        final List<User> userList = Arrays.asList(user);
        when(mockUserService.findByStatusUsed()).thenReturn(userList);

        // Run the test
        entityContractControllerUnderTest.upload();

        // Verify the results
    }

    @Test
    public void testUpload_UserServiceReturnsNoItems() {
        // Setup
        when(mockUserService.findByStatusUsed()).thenReturn(Collections.emptyList());

        // Run the test
        entityContractControllerUnderTest.upload();

        // Verify the results
    }

    @Test
    public void testCreateEntity() throws Exception {
        // Setup
        // Run the test
        entityContractControllerUnderTest.createEntity();

        // Verify the results
    }

    @Test(expected = ContractException.class)
    public void testCreateEntity_ThrowsContractException() throws Exception {
        // Setup
        // Run the test
        entityContractControllerUnderTest.createEntity();
    }

    @Test(expected = TimeoutException.class)
    public void testCreateEntity_ThrowsTimeoutException() throws Exception {
        // Setup
        // Run the test
        entityContractControllerUnderTest.createEntity();
    }

    @Test(expected = InterruptedException.class)
    public void testCreateEntity_ThrowsInterruptedException() throws Exception {
        // Setup
        // Run the test
        entityContractControllerUnderTest.createEntity();
    }

    @Test
    public void testDownload() {
        // Setup
        // Run the test
        entityContractControllerUnderTest.download();

        // Verify the results
    }

    @Test
    public void testShowdown() throws Exception {
        // Setup
        // Run the test
        entityContractControllerUnderTest.showdown();

        // Verify the results
    }

    @Test(expected = ContractException.class)
    public void testShowdown_ThrowsContractException() throws Exception {
        // Setup
        // Run the test
        entityContractControllerUnderTest.showdown();
    }

    @Test
    public void testUpdate() throws Exception {
        // Setup
        // Configure UserService.findByStatusUsed(...).
        final User user = new User();
        final List<User> userList = Arrays.asList(user);
        when(mockUserService.findByStatusUsed()).thenReturn(userList);

        // Run the test
        entityContractControllerUnderTest.update();

        // Verify the results
    }

    @Test
    public void testUpdate_UserServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockUserService.findByStatusUsed()).thenReturn(Collections.emptyList());

        // Run the test
        entityContractControllerUnderTest.update();

        // Verify the results
    }

    @Test
    public void testPostUpdate() throws Exception {
        // Setup
        // Run the test
        entityContractControllerUnderTest.postUpdate();

        // Verify the results
    }

    @Test(expected = ContractException.class)
    public void testPostUpdate_ThrowsContractException() throws Exception {
        // Setup
        // Run the test
        entityContractControllerUnderTest.postUpdate();
    }

    @Test(expected = TimeoutException.class)
    public void testPostUpdate_ThrowsTimeoutException() throws Exception {
        // Setup
        // Run the test
        entityContractControllerUnderTest.postUpdate();
    }

    @Test(expected = InterruptedException.class)
    public void testPostUpdate_ThrowsInterruptedException() throws Exception {
        // Setup
        // Run the test
        entityContractControllerUnderTest.postUpdate();
    }

    @Test
    public void testUpdateUploader() throws Exception {
        // Setup
        // Configure UserService.findByStatusUsed(...).
        final User user = new User();
        final List<User> userList = Arrays.asList(user);
        when(mockUserService.findByStatusUsed()).thenReturn(userList);

        // Run the test
        entityContractControllerUnderTest.updateUploader();

        // Verify the results
    }

    @Test
    public void testUpdateUploader_UserServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockUserService.findByStatusUsed()).thenReturn(Collections.emptyList());

        // Run the test
        entityContractControllerUnderTest.updateUploader();

        // Verify the results
    }

    @Test
    public void testPostUpdateUploader() throws Exception {
        // Setup
        // Run the test
        entityContractControllerUnderTest.postUpdateUploader();

        // Verify the results
    }

    @Test(expected = ContractException.class)
    public void testPostUpdateUploader_ThrowsContractException() throws Exception {
        // Setup
        // Run the test
        entityContractControllerUnderTest.postUpdateUploader();
    }

    @Test(expected = TimeoutException.class)
    public void testPostUpdateUploader_ThrowsTimeoutException() throws Exception {
        // Setup
        // Run the test
        entityContractControllerUnderTest.postUpdateUploader();
    }

    @Test(expected = InterruptedException.class)
    public void testPostUpdateUploader_ThrowsInterruptedException() throws Exception {
        // Setup
        // Run the test
        entityContractControllerUnderTest.postUpdateUploader();
    }

    @Test
    public void testUpdateCooperator() throws Exception {
        // Setup
        // Configure UserService.findByStatusUsed(...).
        final User user = new User();
        final List<User> userList = Arrays.asList(user);
        when(mockUserService.findByStatusUsed()).thenReturn(userList);

        // Run the test
        entityContractControllerUnderTest.updateCooperator();

        // Verify the results
    }

    @Test
    public void testUpdateCooperator_UserServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockUserService.findByStatusUsed()).thenReturn(Collections.emptyList());

        // Run the test
        entityContractControllerUnderTest.updateCooperator();

        // Verify the results
    }

    @Test
    public void testPostUpdateCooperator() throws Exception {
        // Setup
        // Run the test
        entityContractControllerUnderTest.postUpdateCooperator();

        // Verify the results
    }

    @Test(expected = ContractException.class)
    public void testPostUpdateCooperator_ThrowsContractException() throws Exception {
        // Setup
        // Run the test
        entityContractControllerUnderTest.postUpdateCooperator();
    }

    @Test(expected = TimeoutException.class)
    public void testPostUpdateCooperator_ThrowsTimeoutException() throws Exception {
        // Setup
        // Run the test
        entityContractControllerUnderTest.postUpdateCooperator();
    }

    @Test(expected = InterruptedException.class)
    public void testPostUpdateCooperator_ThrowsInterruptedException() throws Exception {
        // Setup
        // Run the test
        entityContractControllerUnderTest.postUpdateCooperator();
    }

    @Test
    public void testUpdateDownloader() throws Exception {
        // Setup
        // Configure UserService.findByStatusUsed(...).
        final User user = new User();
        final List<User> userList = Arrays.asList(user);
        when(mockUserService.findByStatusUsed()).thenReturn(userList);

        // Run the test
        entityContractControllerUnderTest.updateDownloader();

        // Verify the results
    }

    @Test
    public void testUpdateDownloader_UserServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockUserService.findByStatusUsed()).thenReturn(Collections.emptyList());

        // Run the test
        entityContractControllerUnderTest.updateDownloader();

        // Verify the results
    }

    @Test
    public void testPostUpdateDownloader() throws Exception {
        // Setup
        // Run the test
        entityContractControllerUnderTest.postUpdateDownloader();

        // Verify the results
    }

    @Test(expected = ContractException.class)
    public void testPostUpdateDownloader_ThrowsContractException() throws Exception {
        // Setup
        // Run the test
        entityContractControllerUnderTest.postUpdateDownloader();
    }

    @Test(expected = TimeoutException.class)
    public void testPostUpdateDownloader_ThrowsTimeoutException() throws Exception {
        // Setup
        // Run the test
        entityContractControllerUnderTest.postUpdateDownloader();
    }

    @Test(expected = InterruptedException.class)
    public void testPostUpdateDownloader_ThrowsInterruptedException() throws Exception {
        // Setup
        // Run the test
        entityContractControllerUnderTest.postUpdateDownloader();
    }

    @Test
    public void testDelete() throws Exception {
        // Setup
        // Run the test
        entityContractControllerUnderTest.delete();

        // Verify the results
    }

    @Test(expected = InterruptedException.class)
    public void testDelete_ThrowsInterruptedException() throws Exception {
        // Setup
        // Run the test
        entityContractControllerUnderTest.delete();
    }

    @Test(expected = TimeoutException.class)
    public void testDelete_ThrowsTimeoutException() throws Exception {
        // Setup
        // Run the test
        entityContractControllerUnderTest.delete();
    }

    @Test(expected = ContractException.class)
    public void testDelete_ThrowsContractException() throws Exception {
        // Setup
        // Run the test
        entityContractControllerUnderTest.delete();
    }

    @Test
    public void testIPFSUpload() throws Exception {
        // Setup
        // Run the test
        entityContractControllerUnderTest.IPFSUpload();

        // Verify the results
    }

    @Test(expected = IOException.class)
    public void testIPFSUpload_ThrowsIOException() throws Exception {
        // Setup
        // Run the test
        entityContractControllerUnderTest.IPFSUpload();
    }

    @Test
    public void testIPFSTest() throws Exception {
        // Setup
        // Run the test
        entityContractControllerUnderTest.IPFSTest();

        // Verify the results
    }

    @Test(expected = IOException.class)
    public void testIPFSTest_ThrowsIOException() throws Exception {
        // Setup
        // Run the test
        entityContractControllerUnderTest.IPFSTest();
    }
}
