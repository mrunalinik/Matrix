package com.example.matrix.matrix;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.matrix.matrix.R.id.no_of_columns_spinner;
import static com.example.matrix.matrix.R.id.no_of_rows_spinner;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by mrunalinikoritala on 9/27/17.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ResultActivityTest {


    @Rule
    ActivityTestRule<ResultActivity> resultActivityTestRule
            = new ActivityTestRule<ResultActivity>(ResultActivity.class,true, true) {
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            Intent result = new Intent(targetContext, MainActivity.class);
            result.putExtra("no_of_columns", "3");
            result.putExtra("no_of_rows", "3");
            return result;
        }
    };

    @Test
    public void newTest(){

    onView(allOf(withId(R.id.find_path_btn), withText("Find Path"), isDisplayed())).perform(click());

    onView(withText("3")).perform(scrollTo(), replaceText("2"), closeSoftKeyboard());

    onView(withText("5")).perform(scrollTo(), replaceText("3"), closeSoftKeyboard());

    onView(withText("8")).perform(scrollTo(), replaceText("4"), closeSoftKeyboard());

    onView(withText("6")).perform(scrollTo(), replaceText("5"), closeSoftKeyboard());

    onView(allOf(withId(R.id.find_path_btn), withText("Find Path"), isDisplayed())).perform(click());

        Espresso.pressBack();

        onView(allOf(withContentDescription("Navigate up"),withParent(withId(R.id.toolbar)),isDisplayed())).perform(click());

    }
}
