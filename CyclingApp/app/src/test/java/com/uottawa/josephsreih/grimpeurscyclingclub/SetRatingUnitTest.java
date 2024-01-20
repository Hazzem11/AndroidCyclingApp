package com.uottawa.josephsreih.grimpeurscyclingclub;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class SetRatingUnitTest {

    private static final float FAKE_USERNAME_STRING = 3.14f ;



    @Test
    public void testRatingSet() {

        Rating rating = new Rating(2.14f, "was great");
        rating.setRating(3.14f);


        float result = rating.getRating();

        assertThat(result, is(FAKE_USERNAME_STRING));
    }
}
