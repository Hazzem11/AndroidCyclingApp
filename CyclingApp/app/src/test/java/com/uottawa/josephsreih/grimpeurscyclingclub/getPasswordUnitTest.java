package com.uottawa.josephsreih.grimpeurscyclingclub;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class getPasswordUnitTest {
    private static final String FAKE_USERNAME_STRING = "password" ;


    @Test
    public void testUser() {

        User user = new User("joe","joe@email.com","password","1234f");
        String result = user.getPassword();
        assertThat(result, is(FAKE_USERNAME_STRING));
    }
}
