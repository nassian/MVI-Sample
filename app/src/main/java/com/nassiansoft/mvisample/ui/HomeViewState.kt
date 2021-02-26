package com.nassiansoft.mvisample.ui

import com.nassiansoft.mvisample.data.model.User

sealed class HomeViewState{
    object Idle:HomeViewState()
    object Loading:HomeViewState()
    class Data(val user:User):HomeViewState()
}
