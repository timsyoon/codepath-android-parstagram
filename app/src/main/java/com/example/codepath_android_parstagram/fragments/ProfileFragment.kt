package com.example.codepath_android_parstagram.fragments

import android.util.Log
import com.example.codepath_android_parstagram.Post
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser

class ProfileFragment: FeedFragment() {

    override fun queryPosts() {
        // Specify which class to query
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        // Find all Post objects
        query.include(Post.KEY_USER)
        // Only return posts from the currently signed in user
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser())
        // Return posts in descending order (newest posts first)
        query.addDescendingOrder("createdAt")
        // Return the 20 most recent posts. POST_LIMIT defined in companion object
        query.setLimit(POST_LIMIT)

        query.findInBackground(object : FindCallback<Post> {
            override fun done(posts: MutableList<Post>?, e: ParseException?) {
                if (e != null) {
                    // Something went wrong
                    Log.e(TAG, "Error fetching posts")
                } else {
                    if (posts != null) {
                        for (post in posts) {
                            Log.i(TAG, "Post: " + post.getDescription() + " , username: " +
                                    post.getUser()?.username)
                        }
                        adapter.clear()
                        adapter.addAll(posts)
                        // Signal that the refresh has finished
                        swipeContainer.setRefreshing(false)
                    }
                }
            }
        })
    }
}