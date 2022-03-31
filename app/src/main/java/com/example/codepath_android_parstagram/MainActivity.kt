package com.example.codepath_android_parstagram

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.codepath_android_parstagram.fragments.ComposeFragment
import com.example.codepath_android_parstagram.fragments.FeedFragment
import com.example.codepath_android_parstagram.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser


/**
 * Let user create a post by taking a photo with their camera
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager: FragmentManager = supportFragmentManager
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener {
            item ->

            var fragmentToShow: Fragment? = null

            when (item.itemId) {
                R.id.action_home -> {
                    fragmentToShow = FeedFragment()
                }
                R.id.action_compose -> {
                    fragmentToShow = ComposeFragment()
                }
                R.id.action_profile -> {
                    fragmentToShow = ProfileFragment()
                }
            }

            if (fragmentToShow != null) {
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragmentToShow).commit()
            }

            // Return true to say that we've handled this user interaction on the item
            true
        }

        // Set default selection
        bottomNavigationView.selectedItemId = R.id.action_home

        // queryPosts()
    }

    // Source: https://guides.codepath.com/android/Defining-The-ActionBar#adding-action-items
    override fun onCreateOptionsMenu(menu: Menu) : Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // Source: https://guides.codepath.com/android/Defining-The-ActionBar#handling-actionbar-clicks
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar items
        if (item.getItemId() == R.id.miSignout) {
            ParseUser.logOutInBackground { e ->
                if (e == null) {
                    goToLoginActivity()
                    Log.i(TAG, "Sign out successful")
                    Toast.makeText(this, "Sign out successful", Toast.LENGTH_SHORT)
                } else {
                    Log.e(TAG, "Sign out failed")
                    Toast.makeText(this, "Sign out failed", Toast.LENGTH_SHORT)
                }
            }
            val currentUser = ParseUser.getCurrentUser() // this will now be null
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    private fun goToLoginActivity() {
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        const val TAG = "MainActivity"
    }
}