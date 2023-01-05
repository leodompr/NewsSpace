package com.uruklabs.newsspace.presentation.ui.home

import androidx.lifecycle.*
import com.uruklabs.newsspace.core.RemoteException
import com.uruklabs.newsspace.core.State
import com.uruklabs.newsspace.data.SpaceFlightNewsCategory
import com.uruklabs.newsspace.data.model.Post
import com.uruklabs.newsspace.domain.GetLatestPostsUsecase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * Essa classe dá suporte à tela principal (Home).
 */
class HomeViewModel(private val getLatestPostsUsecase: GetLatestPostsUsecase) : ViewModel() {

    private val _progressBarVisible = MutableLiveData(false)
    val progressBarVisible: LiveData<Boolean>
        get() = _progressBarVisible


    fun showProgressBar() {
        _progressBarVisible.value = true
    }

    fun hideProgressBar() {
        _progressBarVisible.value = false
    }

    private val _snackBar = MutableLiveData<String?>(null)
    val snackbar: LiveData<String?>
        get() = _snackBar

    fun onSnackBarShown() {
        _snackBar.value = null
    }

    private val _listPost = MutableLiveData<State<List<Post>>>()
    val listPost: LiveData<State<List<Post>>>
        get() = _listPost

    init {
        fetchPosts()
    }

    fun fethLatest(category: SpaceFlightNewsCategory) {
        fetchPosts(category)
    }


    /**
     * Esse método coleta o fluxo do repositorio e atribui
     * o seu valor ao campo _listPost
     */
    private fun fetchPosts(query : String) {
        viewModelScope.launch {
            getLatestPostsUsecase(query)
                .onStart {
                    //Faça algo no comecço do flow
                    _listPost.postValue(State.Loading)
                    delay(800) //efeito cosmético
                }
                .catch {
                    //trate uma Exc
                    val exception = RemoteException("Unable to connect to SpaceFlightNews API")
                    _listPost.postValue(State.Error(exception))
                    _snackBar.value = exception.message
                }
                .collect {
                    _listPost.value = State.Success(it)
                }
        }
    }

    val helloText = Transformations.map(listPost) { state ->
        when (state) {
            State.Loading -> {
                " 🌌 Loading latest news..."
            }

            is State.Error -> {
                "Houston, we've had a problem!!"
            }
            else -> {
                " "
            }
        }
    }

}
