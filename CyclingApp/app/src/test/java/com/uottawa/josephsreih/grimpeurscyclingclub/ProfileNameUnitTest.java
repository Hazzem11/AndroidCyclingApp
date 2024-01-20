package com.uottawa.josephsreih.grimpeurscyclingclub;

import org.junit.Test;
import org.junit.Test;
import android.content.Context;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class ProfileNameUnitTest {


    private static final String FAKE_USERNAME_STRING = "Timothy" ;


    @Test
    public void testSetName() {

        Participant joe = new Participant("joe", "bill","aakin@gmail.com","4fg65","i867");
        Profile newuser = new Profile("sfsf","edrfrf","rfffrf",joe);

        newuser.setName("Timothy");
        String result = newuser.getName();

        assertThat(result, is(FAKE_USERNAME_STRING));
    }
}
