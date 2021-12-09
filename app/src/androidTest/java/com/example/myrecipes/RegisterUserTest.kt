package com.example.myrecipes

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.provider.ContactsContract.Directory.PACKAGE_NAME
import android.view.WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
import android.view.WindowManager.LayoutParams.TYPE_TOAST
import androidx.core.content.ContextCompat.startActivity
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Root
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.firebase.auth.FirebaseAuth
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.Description
import org.junit.runner.RunWith

private const val EMAIL = "tester@tested.com"
private const val PACKAGE_NAME = "com.example.myrecipes"
private const val EXTRA_MESSAGE = "tester@tested.com"

@RunWith(AndroidJUnit4::class)

class RegisterUserTest {


    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(RegisterPage::class.java)

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun useRegisterButton() {
        mockkStatic(FirebaseAuth::class)
        every { FirebaseAuth.getInstance() } returns mockk(relaxed = true)

        onView(withId(R.id.etRegisterUsername)).perform(typeText(EMAIL),
            pressImeActionButton())

        onView(withId(R.id.etRegisterPassword)).perform(typeText("978657"),
            pressImeActionButton())

       intended(hasComponent(RecipeHomePage::class.java!!.name))

        onView(withId(R.id.btnRegisterUser)).perform(click(),
            pressImeActionButton())

//        intended(allOf(
//            hasComponent(hasShortClassName(".DisplayMessageActivity")),
//            toPackage(com.example.myrecipes.PACKAGE_NAME),
//            hasExtra(RegisterPage.EXTRA_MESSAGE,EMAIL)))

        val activityScenarioRule = ActivityScenario.launch(RecipeHomePage::class.java)
        onView(withId(R.id.tvCurrentUser)).check(matches((withText(EMAIL))))

//        startActivity(Intent(this, RegisterPage::class.java))
//        onView(withId(R.id.tvCurrentUser)).check(matches((withText("tester@tested.com"))))

    }
}