package com.nassiansoft.mvisample.ui

sealed class HomeViewIntent{
    class GetUser(val id:Int):HomeViewIntent()
}
