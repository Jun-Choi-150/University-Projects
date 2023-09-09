package com.example.sumon.androidvolley;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

public class PersonalAccountSystemTests {
    @Rule
    public ActivityScenarioRule<PersonalAccountActivity> rule2 = new ActivityScenarioRule<>(PersonalAccountActivity.class);
    @Test
    public void PersonalAccountTest() throws InterruptedException {
        // Context of the app under test.
        onView(withId(R.id.friendsList)).perform(click());
        Thread.sleep(3000);

        onView(withId(R.id.backButton1)).check(matches(isDisplayed()));

    }
}
