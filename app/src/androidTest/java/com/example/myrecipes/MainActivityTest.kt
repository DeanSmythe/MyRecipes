package com.example.myrecipes

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myrecipes.utils.FirebaseUtils
import com.example.myrecipes.utils.FirebaseUtils.firebaseAuth
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val EMAIL = "tester8@tested.com"
private const val PASSWORD = "978657"

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

        @get:Rule
        val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

        @Test
        fun useSignInButton() {
            Intents.init()
            mockkStatic(FirebaseAuth::class)

            Espresso.onView(ViewMatchers.withId(R.id.email)).perform(
                ViewActions.typeText(EMAIL),
                ViewActions.pressImeActionButton()
            )

            Espresso.onView(ViewMatchers.withId(R.id.password)).perform(
                ViewActions.typeText(PASSWORD),
                ViewActions.pressImeActionButton()
            )

            Espresso.onView(ViewMatchers.withId(R.id.login_button)).perform(ViewActions.click())
            Thread.sleep(500)
            Intents.intended(hasComponent(RecipeHomePage::class.java.name))

            Espresso.onView(ViewMatchers.withText(EMAIL))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

            Intents.release()
        }

}