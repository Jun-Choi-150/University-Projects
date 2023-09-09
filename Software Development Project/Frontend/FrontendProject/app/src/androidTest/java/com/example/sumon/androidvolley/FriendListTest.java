package com.example.sumon.androidvolley;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class FriendListTest {
    @Rule
    public ActivityScenarioRule<FriendListActivity> rule4 = new ActivityScenarioRule<>(FriendListActivity.class);
    @Test
    public void FriendPageTest() throws InterruptedException {
        // Context of the app under test.
        onView(withId(R.id.backButton1)).perform(click());
        Thread.sleep(3000);

        onView(withId(R.id.friendsList)).check(matches(isDisplayed()));

    }

}

