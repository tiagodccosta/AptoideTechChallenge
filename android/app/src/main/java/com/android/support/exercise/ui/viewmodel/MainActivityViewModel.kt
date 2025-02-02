package com.android.support.exercise.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.support.exercise.bd.AppDatabase
import com.android.support.exercise.bd.entities.User
import com.android.support.exercise.data.Post
import com.android.support.exercise.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel : ViewModel() {

    private val _userName: MutableLiveData<String?> = MutableLiveData("Loading name...")
    val userName: MutableLiveData<String?> get() = _userName

    private val _userImage: MutableLiveData<String?> =
        MutableLiveData("https://cdn-icons-png.flaticon.com/512/9815/9815472.png")
    val userImage: LiveData<String?> get() = _userImage

    private val _posts: MutableLiveData<ArrayList<Post>> =
        MutableLiveData(arrayListOf())
    val posts: LiveData<ArrayList<Post>> get() = _posts

    fun loadPostsData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val posts = RetrofitClient.apiService.getPosts()
                CoroutineScope(Dispatchers.Main).launch {
                    _posts.value = posts.toCollection(arrayListOf())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("ErrorLoadingPostsData", "Error Loading posts data.")
            }
        }
    }

    fun loadUserData(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val database = AppDatabase.getDatabase(context)
                AppDatabase.populateRoomDatabase(database)

                val usersLiveData: LiveData<List<User>> = database.userDao().getAllUsers()

                withContext(Dispatchers.Main) {
                    usersLiveData.observeForever { users ->
                        if (users.isNotEmpty()) {
                            _userName.value = users[0].name
                            _userImage.value = users[0].image
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("ErrorLoadingUserData", "Error Loading user data.")
            }
        }
    }
}