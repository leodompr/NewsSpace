package com.uruklabs.newsspace.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uruklabs.newsspace.core.Query
import com.uruklabs.newsspace.core.RemoteException
import com.uruklabs.newsspace.core.State
import com.uruklabs.newsspace.data.SpaceFlightNewsCategory
import com.uruklabs.newsspace.data.entites.model.Post
import com.uruklabs.newsspace.domain.GetLatestPostsByTitleUseCase
import com.uruklabs.newsspace.domain.GetLatestPostsUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * Essa classe dá suporte à tela principal (Home).
 */

class HomeViewModel(
    private val getLatestPostsUsecase: GetLatestPostsUseCase,
    private val getLatestPostsByTitleUseCase: GetLatestPostsByTitleUseCase
) : ViewModel() {

    private val _progressBarVisible = MutableLiveData(false)
    val progressBarVisible: LiveData<Boolean>
        get() = _progressBarVisible

    private fun showProgressBar() {
        _progressBarVisible.value = true
    }

    private fun hideProgressBar() {
        _progressBarVisible.value = false
    }

    private val _snackBar = MutableLiveData<String?>(null)
    val snackbar: LiveData<String?>
        get() = _snackBar

    /**
     * Remove a snackBar após ela ser exibida
     * **/
    fun onSnackBarShown() {
        _snackBar.value = null
    }

    /** Configura o que esta sendo exibido da seleção do Menu, por padrão ao iniciar é exibido os Artigos **/
    private val _category = MutableLiveData<SpaceFlightNewsCategory>().apply {
        value = SpaceFlightNewsCategory.ARTICLES
    }
    val category: LiveData<SpaceFlightNewsCategory>
        get() = _category

    private val _listPost = MutableLiveData<State<List<Post>>>()
    val listPost: LiveData<State<List<Post>>>
        get() = _listPost

    init {
        fethLatest(_category.value ?: SpaceFlightNewsCategory.ARTICLES)
    }

    fun fethLatest(category: SpaceFlightNewsCategory) {
        showProgressBar()
        fetchPosts(Query(type = category.value))
    }

    /**
     * Esse método coleta o fluxo do repositorio e atribui
     * o seu valor ao campo _listPost
     */
    private fun fetchPosts(query: Query) {
        viewModelScope.launch {
            getLatestPostsUsecase(query)
                .onStart {
                    // Faça algo no comecço do flow
                    showProgressBar()
                    _listPost.postValue(State.Loading)
                }
                .catch {
                    // trate uma Exc
                    hideProgressBar()
                    val exception = RemoteException("Unable to connect to SpaceFlightNews API")
                    _listPost.postValue(State.Error(exception))
                    _snackBar.value = exception.message
                }
                .collect {
                    hideProgressBar()
                    it.data?.let { list ->
                        _listPost.value = State.Success(list)
                    }
                    it.error?.let { errorMsg ->
                        _snackBar.value = errorMsg.message
                    }

                    _category.value = enumValueOf<SpaceFlightNewsCategory>(query.type.uppercase())
                }
        }
    }

    private fun fetchPostsByTitle(query: Query) {
        viewModelScope.launch {
            getLatestPostsByTitleUseCase(query)
                .onStart {
                    // Faça algo no comecço do flow
                    _listPost.postValue(State.Loading)
                }
                .catch {
                    // trate uma Exc
                    val exception = RemoteException("Unable to connect to SpaceFlightNews API")
                    _listPost.postValue(State.Error(exception))
                    _snackBar.value = exception.message
                }
                .collect {
                    it.data?.let { list ->
                        _listPost.value = State.Success(list)
                    }
                    it.error?.let { errorMsg ->
                        _snackBar.value = errorMsg.message
                    }
                    _category.value = enumValueOf<SpaceFlightNewsCategory>(query.type.uppercase())
                }
        }
    }

    fun searchPostsByTile(queryStr: String) {
        _category.value?.let {
            fetchPostsByTitle(Query(type = it.value, query = queryStr))
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
