package com.behabits.gymbo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
class GymboApplicationTests {

    @Test
    void contextLoads() {
        assertThat("Context loads", true);
    }

}
