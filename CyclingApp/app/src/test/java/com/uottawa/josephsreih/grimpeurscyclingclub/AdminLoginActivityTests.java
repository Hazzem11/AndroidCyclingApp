package com.uottawa.josephsreih.grimpeurscyclingclub;

import org.junit.Test;
import android.content.Context;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class AdminLoginActivityTests {

    private static final String FAKE_STRING = "Login was successful";

    private static final String FAKE_USERNAME_STRING = "Email entry is valid";


    Context mMockContext;

    @Test
    public void readStringFromContext_LocalizedString() {

        AdminLoginActivity myObjectUnderTest = new AdminLoginActivity();

        // ...when the string is returned from the object under test...
        String result = myObjectUnderTest.validate("admin","admin");

        // ...then the result should be the expected one.
        assertThat(result, is(FAKE_STRING));
    }

    @Test
    public void testValidUsername(){
        AdminLoginActivity myObjectUnderTest = new AdminLoginActivity();

        String result = myObjectUnderTest.validate_email("admin123@uottawa.ca");

        assertThat(result, is(FAKE_USERNAME_STRING));
    }

    @Test
    public void testInvalidUsername(){
        AdminLoginActivity myObjectUnderTest = new AdminLoginActivity();

        String result = myObjectUnderTest.validate_email("!!!!@g1$.com");

        assertThat(result, is(FAKE_USERNAME_STRING));

    }




}
