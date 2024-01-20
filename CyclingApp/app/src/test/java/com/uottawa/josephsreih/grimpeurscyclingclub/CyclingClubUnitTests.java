package com.uottawa.josephsreih.grimpeurscyclingclub;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class CyclingClubUnitTests {

    private static final String name = "TrekBiking" ;
    private static final String testing = "234S" ;



    @Test
    public void testCyclingClubID() {

        CyclingClub user = new CyclingClub("CCM","joe@email.com","password", "1234567");
        user.setId("234S");
        String result = user.getId();
        assertThat(result, is(testing));

    }

    @Test
    public void testCyclingClubName() {

        CyclingClub user = new CyclingClub("CCM","joe@email.com","password", "12345678");
        user.setClubName("TrekBiking");
        String result = user.getClubName();
        assertThat(result, is(name));

    }
}
