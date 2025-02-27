package com.example.service


//This is a user session to track the log in user to display slots based on user
class UserSession private constructor() {

    var loggedInUserId: Int = -1

    companion object {
        @Volatile
        private var instance: UserSession? = null

        fun getInstance(): UserSession {
            return instance ?: synchronized(this) {
                instance ?: UserSession().also { instance = it }
            }
        }
    }
}
