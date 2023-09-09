package com.example.sumon.androidvolley;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

public class PostTest {
    @Rule
    public ActivityScenarioRule<MoviePostActivity> rule3 = new ActivityScenarioRule<>(MoviePostActivity.class);
    @Test
    public void SuccessfulPostTest() throws InterruptedException {
        onView(withId(R.id.inputBox)).perform(typeText("good!"),closeSoftKeyboard());
        onView(withId(R.id.postStar3)).perform(click());
        Thread.sleep(3000);
        onView(withId(R.id.postButton)).perform(click());
        Thread.sleep(3000);
        onView(withId(R.id.createPostButton)).check(matches(isDisplayed()));

    }

    @Test
    public void MoviePostFailTest() throws InterruptedException {
//        onView(withId(R.id.inputBox)).perform(typeText("good!"),closeSoftKeyboard());
        onView(withId(R.id.postStar3)).perform(click());
        Thread.sleep(3000);
        onView(withId(R.id.postButton)).perform(click());
        Thread.sleep(3000);
        onView(withId(R.id.postButton)).check(matches(isDisplayed()));

    }
}
