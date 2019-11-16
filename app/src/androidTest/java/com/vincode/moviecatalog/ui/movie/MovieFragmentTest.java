package com.vincode.moviecatalog.ui.movie;


import androidx.test.espresso.IdlingRegistry;
import androidx.test.rule.ActivityTestRule;

import com.vincode.moviecatalog.R;
import com.vincode.moviecatalog.testing.SingleFragmentActivity;
import com.vincode.moviecatalog.utils.EspressoIdlingResource;
import com.vincode.moviecatalog.utils.RecyclerViewItemCountAssertion;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


public class MovieFragmentTest {

    @Rule
    public final ActivityTestRule<SingleFragmentActivity> activityTestRule = new ActivityTestRule<>(SingleFragmentActivity.class);
    private final MovieFragment movieFragment = new MovieFragment();

    @Before
    public void setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource());
        activityTestRule.getActivity().setFragment(movieFragment);
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource());
    }

    @Test
    public void loadMovies() {
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()));//
        onView(withId(R.id.rv_movies)).check(new RecyclerViewItemCountAssertion(20));
    }
}