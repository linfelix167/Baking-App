package com.felix.bakingapp;

import android.os.SystemClock;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    public static final String RECIPE = "Brownies";

    @Rule
    public ActivityTestRule activityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setupActivity() {
        SystemClock.sleep(3000);
    }

    @Test
    public void testRecipePosition() {
        onView(withId(R.id.recycler_view_recipe))
                .perform(RecyclerViewActions.scrollToPosition(1));
        onView(withText(RECIPE)).check(matches(isDisplayed()));
    }

    @Test
    public void testClickRecipe() {
        onView(withId(R.id.recycler_view_recipe))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.recycler_view_step))
                .check(matches(isDisplayed()));
    }
}
