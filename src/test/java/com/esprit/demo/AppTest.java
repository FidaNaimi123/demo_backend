package com.esprit.demo;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AppTest {

    @Test
    public void testAddition() {
        App app = new App();
        assertEquals(5, app.addition(2, 3));
    }

    @Test
    public void testMessage() {
        App app = new App();
        assertEquals("Hello from Jenkins Pipeline!", app.message());
    }
}
