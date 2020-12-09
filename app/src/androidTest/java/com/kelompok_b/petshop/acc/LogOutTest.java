package com.kelompok_b.petshop.acc;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.kelompok_b.petshop.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LogOutTest {

    @Rule
    public ActivityTestRule<SplashScreen> mActivityTestRule = new ActivityTestRule<>(SplashScreen.class);

    @Test
    public void logOutTest() {
        onView(isRoot()).perform(waitFor(10000));
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.btn_sign_in), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.input_email_login),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_email_login_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("kevinghebre20@gmail.com"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.input_password_login),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_password_login_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("123456"), closeSoftKeyboard());

//        ViewInteraction textInputEditText3 = onView(
//                allOf(withId(R.id.input_password_login), withText("123456"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.input_password_login_layout),
//                                        0),
//                                0),
//                        isDisplayed()));
//        textInputEditText3.perform(click());
//
//        ViewInteraction textInputEditText4 = onView(
//                allOf(withId(R.id.input_password_login), withText("123456"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.input_password_login_layout),
//                                        0),
//                                0),
//                        isDisplayed()));
//        textInputEditText4.perform(pressImeActionButton());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.btn_sign_in), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton2.perform(click());
        onView(isRoot()).perform(waitFor(4000));

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());
        onView(isRoot()).perform(waitFor(1000));

        ViewInteraction navigationMenuItemView = onView(
                allOf(withId(R.id.nav_logout),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                8),
                        isDisplayed()));
        navigationMenuItemView.perform(click());
        onView(isRoot()).perform(waitFor(1000));

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.logout), withText("Hit Me if You Want Log Out!"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                0),
                        isDisplayed()));
        materialButton3.perform(click());
        onView(isRoot()).perform(waitFor(1000));
//
//        ViewInteraction materialButton4 = onView(
//                allOf(withId(R.id.logout), withText("Hit Me if You Want Log Out!"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.nav_host_fragment),
//                                        0),
//                                0),
//                        isDisplayed()));
//        materialButton4.perform(click());

        ViewInteraction materialButton5 = onView(
                allOf(withId(android.R.id.button1), withText("Logout"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton5.perform(scrollTo(), click());
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

    public static ViewAction waitFor(long delay) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "wait for " + delay + "milliseconds";
            }

            @Override
            public void perform(UiController uiController, View view) {
                uiController.loopMainThreadForAtLeast(delay);
            }
        };
    }
}
