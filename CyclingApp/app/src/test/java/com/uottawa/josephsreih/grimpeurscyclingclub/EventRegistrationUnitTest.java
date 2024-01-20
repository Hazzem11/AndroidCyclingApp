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

public class EventRegistrationUnitTest {

    private static final Boolean FAKE_USERNAME_STRING = false ;



    @Test
    public void testEventisFull() {

        EventType x = new EventType("rdrrfr","gtgtg","tgtt");
        Address y = new Address("kanata","k45 89h");
        User z = new User("joe", "joe@gmail.com","893465", "j67");
        Event event = new Event(x,"hillclimb", "ottawa ride" ,"friday 13th",60, 22.5, y,z,59);

        event.setMaxParticipants(70);

        EventRegistration h = new EventRegistration();

        Boolean result = h.eventIsFull(60);

        assertThat(result, is(FAKE_USERNAME_STRING));
    }
}
