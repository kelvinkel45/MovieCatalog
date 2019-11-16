package com.vincode.moviecatalog.ui.tvshow;

import android.content.Context;
import android.content.Intent;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.platform.app.InstrumentationRegistry;
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

public class DetailTvActivityTest {

    //private final TvShow dummyTvShow = FakeDataDummy.generateDummyTvShows().get(0);

    @Rule
    public ActivityTestRule<DetailTvActivity> activityTestRule = new ActivityTestRule<DetailTvActivity>(DetailTvActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            return new Intent(targetContext, DetailTvActivity.class);
        }

    };

    @Before
    public void setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource());
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource());
    }

    @Test
    public void loadTvShow() {

        onView(withId(R.id.tv_title_tv)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_rating_tv)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_language_tv)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_detail_overview_tv)).check(matches(isDisplayed()));
        onView(withId(R.id.img_detail_fav)).check(matches(isDisplayed()));
        onView(withId(R.id.img_detail_fav)).perform(click());

    }

}