package com.example.myrecipes

import android.app.LauncherActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myrecipes.utils.FirebaseUtils.firebaseAuth
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.annotations.PublicApi
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.experimental.results.ResultMatchers.isSuccessful
import org.junit.runner.RunWith

private const val EMAIL = "tester12@tested.com"
private const val PACKAGE_NAME = "com.example.myrecipes"
private const val EXTRA_MESSAGE = "tester@tested.com"

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
        onView(withText("Logged in as: ")).check(matches(withId(R.id.tvCurrentUserTitle)))
        Thread.sleep(500)
        firebaseAuth.getCurrentUser()!!.delete()
        Intents.release()

    }
}



