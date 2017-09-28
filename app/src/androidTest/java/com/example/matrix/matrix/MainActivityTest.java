package com.example.matrix.matrix;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.doubleClick;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.matrix.matrix.matchers.CustomMatchers.withAdaptedData;
import static com.example.matrix.matrix.matchers.CustomMatchers.withItemContent;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Created by mrunalinikoritala on 9/27/17.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    Instrumentation.ActivityMonitor activityMonitor;
    public static final int INVALID_VALUE = 100;
    public static final String VALID_VALUE = "1";
    public static final String FIRST_ROW_ITEM_TEXT = "Select No Of Rows in Matrix";
    public static final String FIRST_COLUMN_ITEM_TEXT="Select No Of Columns in Matrix";


    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule
            = new ActivityTestRule<MainActivity>(MainActivity.class,true, true);



    @Test
    public void nullOrEmptyRow_verifyErrorMsgForRowDisplayed(){
        onView(withText(R.string.rowsString)).check(matches(isDisplayed()));
        onView(withId(R.id.no_of_rows_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("1"))).perform(click());
    }

    @Test
    public void nullOrEmptyColumn_verifyErrorMsgForColumnDisplayed(){
        onView(withText(R.string.columnsString)).check(matches(isDisplayed()));
        onView(withId(R.id.no_of_columns_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("1"))).perform(click());
    }

    @Test
    public void testRowNotInList() {
        onView(withId(R.id.no_of_rows_spinner)).check(matches(not(withAdaptedData(withItemContent(String.valueOf(INVALID_VALUE))))));
    }

    @Test
    public void testColumnNotInList() {
        onView(withId(R.id.no_of_columns_spinner)).check(matches(not(withAdaptedData(withItemContent(String.valueOf(INVALID_VALUE))))));
    }

    @Test
    public void testRowLabelDoesNotChangeIfFirstItemSelected() {

        onView(withId(R.id.no_of_rows_spinner)).perform(click());

        onData(allOf(CoreMatchers.is(instanceOf(String.class)))).atPosition(0).perform(click());

        onView(withId(R.id.row_label)).check(matches(not(withText(FIRST_ROW_ITEM_TEXT))));
    }

    @Test
    public void testColumnLabelDoesNotChangeIfFirstItemSelected() {

        onView(withId(R.id.no_of_columns_spinner)).perform(click());

        onData(allOf(CoreMatchers.is(instanceOf(String.class)))).atPosition(0).perform(click());

        onView(withId(R.id.column_label)).check(matches(not(withText(FIRST_COLUMN_ITEM_TEXT))));
    }

    @Test
    public void testRowLabelUpdatesIfValidSelected() {

        onView(withId(R.id.no_of_rows_spinner)).perform(click());

        onData(allOf(CoreMatchers.is(instanceOf(String.class)), CoreMatchers.is(VALID_VALUE))).perform(click());

    }

    @Test
    public void testColumnLabelUpdatesIfValidSelected() {

        onView(withId(R.id.no_of_columns_spinner)).perform(click());

        onData(allOf(CoreMatchers.is(instanceOf(String.class)), CoreMatchers.is((VALID_VALUE)))).perform(click());

    }
    @Test
    public void alertRowDialog(){

        onView(allOf(withId(R.id.no_of_rows_spinner), isDisplayed())).perform(click());

        onData(allOf(is(instanceOf(String.class)), is("Select Number"))).perform(click());

        onView(allOf(withId(R.id.no_of_columns_spinner), isDisplayed())).perform(click());

        onData(allOf(is(instanceOf(String.class)), is("5"))).perform(click());

        onView(allOf(withId(R.id.continue_btn), withText(R.string.btnContinue), isDisplayed())).perform(click());

        onView(withText(R.string.error_alert_dialog_rows)).check(matches(isDisplayed()));

        onView(allOf(withId(android.R.id.button1), withText(android.R.string.ok))).perform(scrollTo(), click());
    }
    @Test
    public void alertColumnDialog(){

        onView(allOf(withId(R.id.no_of_rows_spinner), isDisplayed())).perform(click());

        onData(allOf(is(instanceOf(String.class)), is("5"))).perform(click());

        onView(allOf(withId(R.id.no_of_columns_spinner), isDisplayed())).perform(click());

        onData(allOf(is(instanceOf(String.class)), is("Select Number"))).perform(click());

        onView(allOf(withId(R.id.continue_btn), withText(R.string.btnContinue), isDisplayed())).perform(click());

        onView(withText(R.string.error_alert_dialog_columns)).check(matches(isDisplayed()));

        onView(allOf(withId(android.R.id.button1), withText(android.R.string.ok))).perform(scrollTo(), click());
    }
    @Test
    public void alertRowColumnDialog(){

        onView(allOf(withId(R.id.no_of_rows_spinner), isDisplayed())).perform(click());

        onData(allOf(is(instanceOf(String.class)), is("Select Number"))).perform(click());

        onView(allOf(withId(R.id.no_of_columns_spinner), isDisplayed())).perform(click());

        onData(allOf(is(instanceOf(String.class)), is("Select Number"))).perform(click());

        onView(allOf(withId(R.id.continue_btn), withText(R.string.btnContinue), isDisplayed())).perform(click());

        onView(withText(R.string.error_alert_dialog_rows_columns)).check(matches(isDisplayed()));

        onView(allOf(withId(android.R.id.button1), withText(android.R.string.ok))).perform(scrollTo(), click());
    }
    @Test
    public void rowColumnSelected() {

        onView(allOf(withId(R.id.no_of_rows_spinner), isDisplayed())).perform(click());

        onView(allOf(withId(android.R.id.text1), withText("3"), isDisplayed())).perform(click());

        onView(allOf(withId(R.id.no_of_columns_spinner), isDisplayed())).perform(click());

        onView(allOf(withId(android.R.id.text1), withText("3"), isDisplayed())).perform(click());

        onView(allOf(withId(R.id.continue_btn), withText("Continue"), isDisplayed())).perform(click());

    }


}

