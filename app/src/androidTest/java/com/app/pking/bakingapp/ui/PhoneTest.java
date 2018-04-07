package com.app.pking.bakingapp.ui;


import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.app.pking.bakingapp.R;
import com.app.pking.bakingapp.idlingResource.EspressoIdlingResource;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class PhoneTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }

    @Test
    public void phoneTest() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.rv_recipe),
                        childAtPosition(
                                withClassName(is("android.widget.FrameLayout")),
                                1)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.tv_recipe_ingredients),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                1),
                        isDisplayed()));

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.rv_recipe_steps),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                1)));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(4968);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.next_step_button), withText("Go to next step"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                1)));
        appCompatButton.perform(scrollTo(), click());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
