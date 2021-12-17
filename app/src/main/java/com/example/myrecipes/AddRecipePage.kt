package com.example.myrecipes

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.core.view.get
import com.example.myrecipes.utils.FirebaseUtils
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_add_recipe_page.*
import kotlinx.android.synthetic.main.activity_ingredients_method_page.*
import java.lang.Exception

class AddRecipePage : AppCompatActivity() {
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe_page)
        toolbar = findViewById(R.id.toolbar_AddRecipe)
        setSupportActionBar(toolbar)


            // Tabs Customization
            tab_layout_addrecipe.setSelectedTabIndicatorColor(Color.WHITE)
            tab_layout_addrecipe.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_700))
            tab_layout_addrecipe.tabTextColors = ContextCompat.getColorStateList(this, android.R.color.white)

            // Set different Text Color for Tabs for when are selected or not
            //tab_layout.setTabTextColors(R.color.normalTabTextColor, R.color.selectedTabTextColor)

            // Number Of Tabs
            val numberOfTabs = 3

            // Set Tabs in the center
            //tab_layout.tabGravity = TabLayout.GRAVITY_CENTER

            // Show all Tabs in screen
            tab_layout_addrecipe.tabMode = TabLayout.MODE_FIXED

            // Scroll to see all Tabs
            //tab_layout.tabMode = TabLayout.MODE_SCROLLABLE

            // Set Tab icons next to the text, instead of above the text
            tab_layout_addrecipe.isInlineLabel = true

            // Set the ViewPager Adapter
            val adapter = AddRecipeTabsAdapter(supportFragmentManager, lifecycle, numberOfTabs)
            tabs_viewpager_addrecipe.adapter = adapter

            // Enable Swipe
            tabs_viewpager_addrecipe.isUserInputEnabled = true

            // Link the TabLayout and the ViewPager2 together and Set Text & Icons
            TabLayoutMediator(tab_layout_addrecipe, tabs_viewpager_addrecipe) { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "Information"
                        tab.setIcon(R.drawable.ic_baseline_list_24)
                    }
                    1 -> {
                        tab.text = "Ingredients"
                        tab.setIcon(R.drawable.ic_baseline_restaurant_menu_24)

                    }
                    2 -> {
                        tab.text = "Photo"
                        tab.setIcon(R.drawable.ic_baseline_photo_camera_24)
                    }

                }
                // Change color of the icons
                tab.icon?.colorFilter =
                    BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                        Color.WHITE,
                        BlendModeCompat.SRC_ATOP
                    )
            }.attach()


            setCustomTabTitles()

    }

        private fun setCustomTabTitles() {
            val vg = tab_layout_addrecipe.getChildAt(0) as ViewGroup
            val tabsCount = vg.childCount

            for (j in 0 until tabsCount) {
                val vgTab = vg.getChildAt(j) as ViewGroup

                val tabChildCount = vgTab.childCount

                for (i in 0 until tabChildCount) {
                    val tabViewChild = vgTab.getChildAt(i)
                    if (tabViewChild is TextView) {

                        // Change Font and Size
                        tabViewChild.typeface = Typeface.DEFAULT_BOLD
                    }
                }
            }
        }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.account_menu,menu)
        var currentUser = menu?.findItem(R.id.itmLoggedInAs)
        currentUser?.setTitle(setUsername())
        var itmCreateRecipeChangeTitle = menu?.findItem(R.id.itmCreateRecipe)
        itmCreateRecipeChangeTitle?.setTitle("Recipe Page")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.itmLogout -> signOut()
            R.id.itmMyCupboard -> myCupboard()
            R.id.itmCreateRecipe -> redirectRecipePage()
            R.id.itmAddIngredient -> redirectIngAddPage()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun signOut(){
        try {
            FirebaseUtils.firebaseAuth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } catch(e: Exception){
            Toast.makeText(this@AddRecipePage,"Sign out Error", Toast.LENGTH_SHORT).show()
        }

    }

    private fun myCupboard(){
        val intent = Intent(this, MyCupboard::class.java)
        startActivity(intent)
    }

    private fun redirectRecipePage(){
        val intent = Intent(this, RecipeHomePage::class.java)
        startActivity(intent)
    }

    private fun redirectIngAddPage(){
        val intent = Intent(this, IngredientAdder::class.java)
        startActivity(intent)
    }

    private fun setUsername(): String {
        val user = FirebaseUtils.firebaseAuth.currentUser?.email
        if (user != null) {
            Log.d("tag", user)
            val username = user
            return "Signed in as: "+user
        }
        return "Error finding user"
    }
}