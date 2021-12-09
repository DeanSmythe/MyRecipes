package com.example.myrecipes

import android.app.PendingIntent.getActivity
import android.view.WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
import android.view.WindowManager.LayoutParams.TYPE_TOAST
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Root
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.Description
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class RegisterUserTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(RegisterPage::class.java)


    @Test
    fun useRegisterButton() {
        onView(withId(R.id.btnRegisterUser)).perform(click())
        onView(withId(R.id.etRegisterUsername)).perform(typeText("tester@tested.com"),
            pressImeActionButton())
        onView(withId(R.id.etRegisterPassword)).perform(typeText("978657"))
        //onView(withId(R.id.btnRegisterUser)).perform(click())

    }
}