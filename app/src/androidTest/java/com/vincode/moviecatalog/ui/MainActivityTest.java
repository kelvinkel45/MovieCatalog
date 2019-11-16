package com.vincode.moviecatalog.ui;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;

import com.vincode.moviecatalog.R;
import com.vincode.moviecatalog.utils.EspressoIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource());
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource());
    }

    @Test
    public void toDetailMovieActivityTest() {
        onView(withId(R.id.navigation_movie)).check(matches(isDisplayed()));
        onView(withId(R.id.navigation_movie)).perform(click());
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()));

    }

    @Test
    public void toDetailTvShowActivityTest() {
        onView(withId(R.id.navigation_tv)).check(matches(isDisplayed()));
        onView(withId(R.id.navigation_tv)).perform(click());
        onView(withId(R.id.rv_tv)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_tv)).perform(RecyclerViewActions.actionOnItemAtPosition(9, click()));
        onView(withId(R.id.tv_title_tv)).check(matches(isDisplayed()));

    }

    @Test
    public void toFavoriteTest() {
        onView(withId(R.id.navigation_tv)).check(matches(isDisplayed()));
        onView(withId(R.id.navigation_tv)).perform(click());
    }
}
