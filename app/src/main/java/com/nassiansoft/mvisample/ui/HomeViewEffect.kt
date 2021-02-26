package com.nassiansoft.mvisample.ui

sealed class HomeViewEffect{
    class Error(val msg:String):HomeViewEffect()
}
