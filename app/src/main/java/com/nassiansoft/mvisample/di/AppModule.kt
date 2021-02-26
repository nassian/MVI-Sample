package com.nassiansoft.mvisample.di

import com.nassiansoft.mvisample.data.Repository
import com.nassiansoft.mvisample.data.network.UserApi
import com.nassiansoft.mvisample.ui.HomeVmFactory
import org.koin.dsl.module

val appModule = module {

    single { UserApi.create() }
    single { Repository(get()) }
    factory { HomeVmFactory(get()) }
}