package com.nassiansoft.mvisample.data

import com.nassiansoft.mvisample.data.model.User
import com.nassiansoft.mvisample.data.network.UserApi
import kotlinx.coroutines.flow.flow

class Repository(private val userApi: UserApi) {

    fun getUser(id:Int)= flow<User> {
        emit(userApi.getUser(id))
    }
}