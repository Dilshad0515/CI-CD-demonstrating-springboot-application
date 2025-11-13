package com.example.app;

import com.example.app.controller.HelloController;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Basic unit test for HelloController.
 * Expand tests to cover service layer and repositories.
 */
public class HelloControllerTest {

    @Test
    public void testHello() {
        HelloController controller = new HelloController();
        String resp = controller.hello();
        assertThat(resp).contains("Hello from backend");
    }
}
