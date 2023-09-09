package com.example.sumon.androidvolley;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.app.Activity;
import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.sumon.androidvolley", appContext.getPackageName());
    }

    @Rule
    public ActivityScenarioRule<StringRequestActivity> rule  = new ActivityScenarioRule<>(StringRequestActivity.class);

    //failed login attempt test
    @Test
    public void LoginFailTest() throws InterruptedException {
        // Context of the app under test.
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("User_New"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword)).perform(typeText("asgdfh"), closeSoftKeyboard());
        onView(withId(R.id.btnJsonObj)).perform(click());
        Thread.sleep(3000);
        onView(withId(R.id.editTextTextEmailAddress)).check(matches(isDisplayed()));

    }
    //successful login test
    @Test
    public void LoginCorrectTest() throws InterruptedException {
        // Context of the app under test.
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("User_New"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword)).perform(typeText("New123"), closeSoftKeyboard());
        onView(withId(R.id.btnJsonObj)).perform(click());
        Thread.sleep(3000);
        assertEquals(5, StringRequestActivity.currUser);

    }



}

