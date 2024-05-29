package com.sltx.controller;

import com.sltx.entity.model.User;
import com.sltx.service.api.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainControllerTest {

    @Mock
    private UserService mockUserService;

    @InjectMocks
    private MainController mainControllerUnderTest;

    @Test
    public void testIndex() {
        // Setup
        // Run the test
        mainControllerUnderTest.index();

        // Verify the results
    }

    @Test
    public void testLogin() {
        // Setup
        // Run the test
        mainControllerUnderTest.login();

        // Verify the results
    }

    @Test
    public void testCaptcha() {
        // Setup
        // Run the test
        mainControllerUnderTest.captcha();

        // Verify the results
    }

    @Test
    public void testPostLogin() {
        // Setup
        // Configure UserService.findByName(...).
        final User user = new User();
        when(mockUserService.findByName("name")).thenReturn(user);

        // Run the test
        mainControllerUnderTest.postLogin();

        // Verify the results
    }

    @Test
    public void testLogout() {
        // Setup
        // Run the test
        mainControllerUnderTest.logout();

        // Verify the results
    }

    @Test
    public void testWelcome() {
        // Setup
        // Run the test
        mainControllerUnderTest.welcome();

        // Verify the results
    }
}
