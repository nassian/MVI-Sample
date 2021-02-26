package com.nassiansoft.mvisample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nassiansoft.mvisample.data.Repository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository):ViewModel() {
    private val _state:MutableStateFlow<HomeViewState> = MutableStateFlow(HomeViewState.Idle)
    val state:StateFlow<HomeViewState>
    get() = _state
    val intent= Channel<HomeViewIntent>()
    private val viewEffectChannel= Channel<HomeViewEffect>()
    val viewEffectFlow=viewEffectChannel.receiveAsFlow()

    init {
        handelIntent()
    }

    private fun handelIntent() {
        viewModelScope.launch {
            intent.consumeAsFlow().collect {
                when(it){
                    is HomeViewIntent.GetUser -> getUser(it.id)

                }
            }
        }
    }

    private suspend fun getUser(id: Int) {

        repository.getUser(id)
            .onStart {
            _state.value=HomeViewState.Loading
            }.catch {
                _state.value=HomeViewState.Idle
                it.localizedMessage?.let {msg->
                    viewEffectChannel.send(HomeViewEffect.Error(msg))
                }
            }.collect {
                _state.value=HomeViewState.Data(it)
            }
    }

}

class HomeVmFactory(private val repository: Repository):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val vm=HomeViewModel(repository)
        return vm as T
    }
}