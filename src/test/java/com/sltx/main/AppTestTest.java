package com.sltx.main;

import org.junit.Before;
import org.junit.Test;

public class AppTestTest {

    private AppTest appTestUnderTest;

    @Before
    public void setUp() {
        appTestUnderTest = new AppTest();
    }

    @Test
    public void testMain() {
        appTestUnderTest.main();
    }
}
