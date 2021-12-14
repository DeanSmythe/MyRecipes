package com.example.myrecipes


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myrecipes.utils.FirebaseUtils.firebaseAuth
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val EMAIL = "tester12@tested.com"

@RunWith(AndroidJUnit4::class)

class RegisterUserTest {


    @get:Rule
        val activityScenarioRule = ActivityScenarioRule(RegisterPage::class.java)



    @Test
    fun useRegisterButton() {
        Intents.init()

        onView(withId(R.id.btnRegisterUser)).perform(click())
        Thread.sleep(100)

        onView(withId(R.id.etRegisterUsername)).perform(typeText(EMAIL),
            pressImeActionButton())

        onView(withId(R.id.etRegisterPassword)).perform(typeText("978657"),
            pressImeActionButton())

        onView(withId(R.id.btnRegisterUser)).perform(click(),
            pressImeActionButton())
        Intents.intended(hasComponent(RecipeHomePage::class.java.name))
//        onView(withText("Logged in as: ")).check(matches(withId(R.id.tvCurrentUserTitle)))
        Thread.sleep(500)
        firebaseAuth.getCurrentUser()!!.delete()
        Intents.release()

    }
}



